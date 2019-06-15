package org.tim16.booker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.tim16.booker.dto.AdminInfoDTO;
import org.tim16.booker.dto.UserDTO;
import org.tim16.booker.model.admins.AirlineAdmin;
import org.tim16.booker.model.admins.HotelAdmin;
import org.tim16.booker.model.admins.RentACarAdmin;
import org.tim16.booker.model.admins.SysAdmin;
import org.tim16.booker.model.airline.Airline;
import org.tim16.booker.model.hotel.Hotel;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.users.Authority;
import org.tim16.booker.model.users.User;
import org.tim16.booker.model.users.UserAuthorities;
import org.tim16.booker.model.users.UserTokenState;
import org.tim16.booker.security.TokenUtils;
import org.tim16.booker.service.*;


//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManagerUser;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private AirlineService airlineService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RentACarService rentACarService;

    /*
    * Funkcija koja predstavlja prijavu korisnika.
    * Ukoliko su uneti ispravni usernmae i password vraca http status OK i token, a u suprotnom
     * vraca http status BAD REQUEST.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody UserDTO dto) {
        try {
            final Authentication authentication = authenticationManagerUser
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()));

            User user = (User) authentication.getPrincipal();

            if (!user.isEnabled())
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            // Ubaci username + password u kontext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Kreiraj token
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();

            UserTokenState token = new UserTokenState(jwt, expiresIn);
            // Vrati token kao odgovor na uspesno autentifikaciju
            return new ResponseEntity<>(token, HttpStatus.OK);

        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DisabledException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    /*
    * Funkcija za registrovanje novih korisnika.
    * Ukoliko su uneti podaci ispravni, za authority se postavi USER (obican korisnik) i kreira se novi korisnik
    * cije polje enable je postavljeno na false. Korisnik se sacuva u bazi podataka i poziva se funkacija za slanje
    * aktivacionog mail-a.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HttpStatus Register(@RequestBody UserDTO dto) throws AuthenticationException {

        User u = userService.findByUsername(dto.getUsername());
        if(u != null) {
            return HttpStatus.CONFLICT;
        }
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

        RegisteredUser user = new RegisteredUser(dto.getUsername(), bc.encode(dto.getPassword()), dto.getName(), dto.getLastname(),
                dto.getEmail(), dto.getCity(), dto.getPhoneNum());

        List<Authority> authorityList = new ArrayList();
        Authority authority = new Authority();
        authority.setName(UserAuthorities.USER.toString());
        authorityList.add(authority);

        user.setAuthorities(authorityList);

        userDetailsService.create(user);
        mailService.sendActivationLink(user);

        return HttpStatus.OK;
    }

    /*
    * Funkcija koja se realziuje nakon sto se klikne na aktivacioni mail.
    * Ukoliko postoji korisnik sa odredjenim tokenom, njegovo polje enable se postavi na true.
     */
    @RequestMapping(value = "/user-confirm/{token}", method = RequestMethod.GET)
    public HttpStatus confirmRegistration(@PathVariable String token) {
        User user = userService.findByToken(token);

        if (user == null) {
            return HttpStatus.BAD_REQUEST;
        }

        user.setEnabled(true);
        userDetailsService.update(user);

        return HttpStatus.OK;
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {

        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userDetailsService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();

            return new ResponseEntity(new UserTokenState(refreshedToken, expiresIn), HttpStatus.OK);
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return new ResponseEntity(userTokenState, HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<HashMap> changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return new ResponseEntity(result, HttpStatus.ACCEPTED);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }

    /*
    * Registrovanje sistemskog admina ukoliko ne postoji u bazi podataka.
    * Poziva se prilikom ucitavanje pocetne stranice aplikacije.
     */
    @RequestMapping(value = "/default-sys-admin", method = RequestMethod.POST)
    public HttpStatus registerDefaultSysAdmin()  {

        if (userService.findByUsername("sys") == null) {
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            SysAdmin admin = new SysAdmin("sys", bc.encode("sys"));

            List<Authority> authorityList = new ArrayList();
            Authority authority = new Authority();
            authority.setName(UserAuthorities.SYS_ADMIN.toString());
            authorityList.add(authority);

            admin.setAuthorities(authorityList);
            admin.setEnabled(true);
            admin.setPassChanged(true);

            userService.save(admin);
        }
        return HttpStatus.OK;
    }

    /*
    * Registrovanje admina aviokompanije, hotela ili rent a car servisa.
     */
    @RequestMapping(value = "/register-admin", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public HttpStatus registerAdmin(@RequestBody AdminInfoDTO dto) {

        User u = userService.findByUsername(dto.getUsername());
        if(u != null) {
            return HttpStatus.CONFLICT;
        }

        if (dto.getAdminOf().equalsIgnoreCase("airline")) {
            return registerAirineAdmin(dto);
        } else if (dto.getAdminOf().equalsIgnoreCase("hotel")) {
            return registerHotelAdmin(dto);
        } else if (dto.getAdminOf().equalsIgnoreCase("rent-a-car")) {
            return registerRentACarAdmin(dto);
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    /*
    * Registrovanje admina aviokompanije.
     */
    private HttpStatus registerAirineAdmin(AdminInfoDTO dto) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

        AirlineAdmin admin = new AirlineAdmin(dto.getUsername(), bc.encode(dto.getPassword()), dto.getName(), dto.getLastName(),
                dto.getEmail(), dto.getCity(), dto.getPhoneNum());

        List<Authority> authorityList = new ArrayList();
        Authority authority = new Authority();
        authority.setName(UserAuthorities.AIRLINE_ADMIN.toString());
        authorityList.add(authority);

        admin.setAuthorities(authorityList);

        Airline airline = airlineService.findOne(dto.getItemID());
        if (airline == null)
            return HttpStatus.BAD_REQUEST;

        admin.setAirline(airline);
        airline.addAdmin(admin);

        userDetailsService.create(admin);
        airlineService.update(airline);
        mailService.sendActivationLink(admin);

        return HttpStatus.OK;
    }

    /*
     * Registrovanje admina hotela.
     */
    private HttpStatus registerHotelAdmin(AdminInfoDTO dto) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

        HotelAdmin admin = new HotelAdmin(dto.getUsername(), bc.encode(dto.getPassword()), dto.getName(), dto.getLastName(),
                dto.getEmail(), dto.getCity(), dto.getPhoneNum());

        List<Authority> authorityList = new ArrayList();
        Authority authority = new Authority();
        authority.setName(UserAuthorities.HOTEL_ADMIN.toString());
        authorityList.add(authority);

        admin.setAuthorities(authorityList);

        Hotel hotel = hotelService.findOne(dto.getItemID());
        if (hotel == null)
            return HttpStatus.BAD_REQUEST;

        admin.setHotel(hotel);
        hotel.addAdmin(admin);

        userDetailsService.create(admin);
        hotelService.update(hotel);
        mailService.sendActivationLink(admin);

        return HttpStatus.OK;
    }

    /*
     * Registrovanje admina rent a car servisa.
     */
    private HttpStatus registerRentACarAdmin(AdminInfoDTO dto) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

        RentACarAdmin admin = new RentACarAdmin(dto.getUsername(), bc.encode(dto.getPassword()), dto.getName(), dto.getLastName(),
                dto.getEmail(), dto.getCity(), dto.getPhoneNum());

        List<Authority> authorityList = new ArrayList();
        Authority authority = new Authority();
        authority.setName(UserAuthorities.RAC_ADMIN.toString());
        authorityList.add(authority);

        admin.setAuthorities(authorityList);

        RentACar rentACar = rentACarService.findOne(dto.getItemID());
        if (rentACar == null)
            return HttpStatus.BAD_REQUEST;

        admin.setRentACar(rentACar);
        rentACar.addAdmin(admin);

        userDetailsService.create(admin);
        rentACarService.update(rentACar);
        mailService.sendActivationLink(admin);

        return HttpStatus.OK;
    }

    /*
     * Registrovanje sys admina.
     */
    @RequestMapping(value = "/register-sys-admin", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public HttpStatus registerSysAdmin(@RequestBody AdminInfoDTO dto) {

        User u = userService.findByUsername(dto.getUsername());
        if(u != null) {
            return HttpStatus.CONFLICT;
        }

        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

        SysAdmin admin = new SysAdmin(dto.getUsername(), bc.encode(dto.getPassword()), dto.getName(), dto.getLastName(),
                dto.getEmail(), dto.getCity(), dto.getPhoneNum());

        List<Authority> authorityList = new ArrayList();
        Authority authority = new Authority();
        authority.setName(UserAuthorities.SYS_ADMIN.toString());
        authorityList.add(authority);

        admin.setAuthorities(authorityList);

        userDetailsService.create(admin);
        mailService.sendActivationLink(admin);

        return HttpStatus.OK;
    }

    /*
    * Provera da li korisnik sa odredjenom email adresom postoji.
     */
    @RequestMapping(value = "/check-mail/{email}", method = RequestMethod.GET)
    public HttpStatus isEmailExist(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if(user != null)
            return HttpStatus.CONFLICT;
        else
            return HttpStatus.OK;
    }

}

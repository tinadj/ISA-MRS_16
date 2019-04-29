package org.tim16.booker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.tim16.booker.dto.AdminInfoDTO;
import org.tim16.booker.dto.UserDTO;
import org.tim16.booker.model.admins.SysAdmin;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;
import org.tim16.booker.model.utility.UserRoles;
import org.tim16.booker.model.utility.UserTokenState;
import org.tim16.booker.security.TokenUtils;
import org.tim16.booker.service.CustomUserDetailsService;
import org.tim16.booker.service.MailService;
import org.tim16.booker.service.UserService;


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

            // Ubaci username + password u kontext
            SecurityContextHolder.getContext().setAuthentication(authentication);


            User user = (User) authentication.getPrincipal();

            if (!user.isEnabled())
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            // Kreiraj token
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();

            UserTokenState token = new UserTokenState(jwt, expiresIn);
            // Vrati token kao odgovor na uspesno autentifikaciju
            return new ResponseEntity<>(token, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            return HttpStatus.BAD_REQUEST;
        }
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

        RegisteredUser user = new RegisteredUser(dto.getUsername(), bc.encode(dto.getPassword()), dto.getName(), dto.getLastname(),
                dto.getEmail(), dto.getCity(), dto.getPhoneNum());

        List<Authority> authorityList = new ArrayList();
        Authority authority = new Authority();
        authority.setName(UserRoles.USER.toString());
        authorityList.add(authority);

        user.setAuthority(authorityList);

        userDetailsService.create(user);
        //mailService.sendActiationLink(user);

        return HttpStatus.OK;
    }

    /*
    * Funkcija koja se realziuje nakon sto se klikne na aktivacioni mail.
    * Ukoliko postoji korisnik sa odredjenim tokenom, njegovo polje enable se postavi na true.
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public HttpStatus confirmRegistration(@RequestParam String token) {
        User user = userService.findByToken(token);

        if (user == null) {
            return HttpStatus.BAD_REQUEST;
        }

        user.setEnabled(true);
        userDetailsService.update(user);

        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) {

        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userDetailsService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.badRequest().body(userTokenState);
        }
    }


    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
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
    public HttpStatus registerSysAdmin()  {

        if (userService.findByUsername("sys") == null) {
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            SysAdmin admin = new SysAdmin("sys", bc.encode("sys"));

            List<Authority> authorityList = new ArrayList();
            Authority authority = new Authority();
            authority.setName(UserRoles.SYS_ADMIN.toString());
            authorityList.add(authority);

            admin.setAuthorities(authorityList);
            admin.setEnabled(true);

            userService.save(admin);
        }
        return HttpStatus.OK;
    }

    /*
    * Registrovanje admina aviokompanije, hotela ili rent a car servisa.
     */
    //@RequestMapping(value = "/register-admin", method = RequestMethod.POST)
    //public ResponseEntity<?> registerAdmin(@RequestBody AdminInfoDTO dto) {

    //}
}

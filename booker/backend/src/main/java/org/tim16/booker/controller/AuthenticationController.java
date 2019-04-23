package org.tim16.booker.controller;

import java.io.IOException;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.tim16.booker.dto.UserDTO;
import org.tim16.booker.model.admins.SysAdmin;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.users.UserType;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;
import org.tim16.booker.model.utility.UserRoles;
import org.tim16.booker.model.utility.UserTokenState;
import org.tim16.booker.security.TokenUtils;
import org.tim16.booker.service.CustomUserDetailsService;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody UserDTO dto) {
        try {
            final Authentication authentication = authenticationManagerUser
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()));


            // Ubaci username + password u kontext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Kreiraj token
            User user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();

            UserTokenState token = new UserTokenState(jwt, expiresIn);

            // Vrati token kao odgovor na uspesno autentifikaciju
            return new ResponseEntity<>(token, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HttpStatus Register(@RequestBody UserDTO dto) throws AuthenticationException {

        User u = userService.findByUsername(dto.getUsername());
        if(u != null) {
            return HttpStatus.CONFLICT;
        }
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

        RegisteredUser user = new RegisteredUser(dto.getUsername(), bc.encode(dto.getPassword()), dto.getName(), dto.getLastname(),
                dto.getEmail(), dto.getCity(), dto.getPhoneNum(), 0, UserType.REGULAR);

        List<Authority> authorityList = new ArrayList();
        Authority authority = new Authority();
        authority.setName(UserRoles.USER.toString());
        authorityList.add(authority);

        user.setAuthority(authorityList);
        user.setEnabled(true);

        userService.save(user);
        return HttpStatus.OK;
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

    @RequestMapping(value = "/register-sys-admin", method = RequestMethod.POST)
    public HttpStatus registerSysAdmin()  {

        if (!userDetailsService.sysAdminExists()) {
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
}

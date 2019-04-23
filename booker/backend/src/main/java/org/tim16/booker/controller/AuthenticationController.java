package org.tim16.booker.controller;

import java.io.IOException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.tim16.booker.dto.UserDTO;
import org.tim16.booker.model.users.User;
import org.tim16.booker.model.users.UserType;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO dto) throws AuthenticationException {

        final Authentication authentication = authenticationManager
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
        return ResponseEntity.ok(token);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HttpStatus Register(@RequestBody UserDTO dto,
                               HttpServletResponse response) throws AuthenticationException, IOException {

        User u = userService.findByUsername(dto.getUsername());
        if(u != null) {
            return HttpStatus.CONFLICT;
        }
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

        User user = new User(dto.getUsername(), bc.encode(dto.getPassword()), dto.getName(), dto.getLastname(),
                dto.getEmail(), dto.getCity(), dto.getPhoneNum(), 0, UserType.REGULAR);
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
}

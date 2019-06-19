package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.tim16.booker.dto.RoleIdDTO;
import org.tim16.booker.dto.UserDTO;
import org.tim16.booker.model.admins.AirlineAdmin;
import org.tim16.booker.model.admins.HotelAdmin;
import org.tim16.booker.model.admins.RentACarAdmin;
import org.tim16.booker.model.users.Authority;
import org.tim16.booker.model.users.User;
import org.tim16.booker.model.users.UserAuthorities;
import org.tim16.booker.service.CustomUserDetailsService;
import org.tim16.booker.service.UserService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private final String ASSETS_PATH = "C:\\Users\\Public\\Pictures\\assets";

    @GetMapping(path = "/get-role-and-id")
    public ResponseEntity<RoleIdDTO> getRoleAndID() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);

            List<Authority> authorities = user.getAuthorities();
            Integer userID = user.getId();

            RoleIdDTO dto;
            if (authorities.get(0).getName().equals(UserAuthorities.AIRLINE_ADMIN.toString())) {
                AirlineAdmin admin = (AirlineAdmin)user;
                dto =  new RoleIdDTO(authorities.get(0).getName(), admin.getId(), admin.getAirline().getId());
            } else if (authorities.get(0).getName().equals(UserAuthorities.HOTEL_ADMIN.toString())) {
                HotelAdmin admin = (HotelAdmin)user;
                dto =  new RoleIdDTO(authorities.get(0).getName(),  admin.getId(), admin.getHotel().getId());
            } else if (authorities.get(0).getName().equals(UserAuthorities.RAC_ADMIN.toString())) {
                RentACarAdmin admin = (RentACarAdmin)user;
                dto =  new RoleIdDTO(authorities.get(0).getName(),  admin.getId(), admin.getRentACar().getId());
            } else {
                dto = new RoleIdDTO(authorities.get(0).getName(), userID);
            }
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(path = "/update")
    public ResponseEntity<User> update(@RequestBody UserDTO dto) {
        User user = userService.findByUsername(dto.getUsername());

        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setCity(dto.getCity());
        user.setPhoneNum(dto.getPhoneNum());
        return new ResponseEntity<>(userDetailsService.update(user), HttpStatus.OK);
    }

    @PutMapping(path = "/upload-picture", consumes = "image/*")
    public ResponseEntity<String> backUploadPicture(InputStream in,
                                                    @RequestHeader("Image-Extension") String extension) {

        String fileName = UUID.randomUUID().toString() + "." + extension;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            user.setProfilePicture(fileName);
            userService.save(user);

            File assets_dir = new File(ASSETS_PATH);
            if (!assets_dir.exists()){
                assets_dir.mkdir();
            }

            java.nio.file.Path BASE_DIR = Paths.get(ASSETS_PATH);
            try {
                Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(fileName, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/get-admins")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<List<User>> getAdmins( ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<User> users = userService.findAll();
            List<User> result = new ArrayList<>();

            for (User user: users) {
                if (user instanceof AirlineAdmin || user instanceof RentACarAdmin || user instanceof HotelAdmin) {
                    result.add(user);
                }
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
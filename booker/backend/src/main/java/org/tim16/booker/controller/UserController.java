package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import org.tim16.booker.dto.RoleIdDTO;
import org.tim16.booker.model.admins.AirlineAdmin;
import org.tim16.booker.model.admins.HotelAdmin;
import org.tim16.booker.model.admins.RentACarAdmin;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;
import org.tim16.booker.model.utility.UserAuthorities;
import org.tim16.booker.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get-role-and-id", method = RequestMethod.GET)
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
}

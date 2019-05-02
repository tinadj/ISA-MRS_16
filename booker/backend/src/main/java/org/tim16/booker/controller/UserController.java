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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.tim16.booker.dto.RoleIdDTO;
import org.tim16.booker.dto.UserDTO;
import org.tim16.booker.model.admins.AirlineAdmin;
import org.tim16.booker.model.admins.HotelAdmin;
import org.tim16.booker.model.admins.RentACarAdmin;
import org.tim16.booker.model.admins.SysAdmin;
import org.tim16.booker.model.hotel.Hotel;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;
import org.tim16.booker.model.utility.UserAuthorities;
import org.tim16.booker.service.CustomUserDetailsService;
import org.tim16.booker.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

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

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<User> update(@RequestBody UserDTO dto) {

        User u = userService.findByUsername(dto.getUsername());
        if(u == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (u instanceof AirlineAdmin) {
            AirlineAdmin airlineAdmin = (AirlineAdmin)u;
            airlineAdmin.setName(dto.getName());
            airlineAdmin.setLastname(dto.getLastname());
            airlineAdmin.setCity(dto.getCity());
            airlineAdmin.setPhoneNum(dto.getPhoneNum());
            return new ResponseEntity<>(userDetailsService.update(airlineAdmin), HttpStatus.OK);
        } else if (u instanceof RentACarAdmin) {
            RentACarAdmin rentACarAdmin = (RentACarAdmin)u;
            rentACarAdmin.setName(dto.getName());
            rentACarAdmin.setLastname(dto.getLastname());
            rentACarAdmin.setCity(dto.getCity());
            rentACarAdmin.setPhoneNum(dto.getPhoneNum());
            return new ResponseEntity<>(userDetailsService.update(rentACarAdmin), HttpStatus.OK);
        } else  if (u instanceof HotelAdmin) {
            HotelAdmin hotelAdmin = (HotelAdmin)u;
            hotelAdmin.setName(dto.getName());
            hotelAdmin.setLastname(dto.getLastname());
            hotelAdmin.setCity(dto.getCity());
            hotelAdmin.setPhoneNum(dto.getPhoneNum());
            return new ResponseEntity<>(userDetailsService.update(hotelAdmin), HttpStatus.OK);
        } else if (u instanceof SysAdmin) {
            SysAdmin sysAdmin = (SysAdmin)u;
            sysAdmin.setName(dto.getName());
            sysAdmin.setLastname(dto.getLastname());
            sysAdmin.setCity(dto.getCity());
            sysAdmin.setPhoneNum(dto.getPhoneNum());
            return new ResponseEntity<>(userDetailsService.update(sysAdmin), HttpStatus.OK);
        } else {
            RegisteredUser user = (RegisteredUser)u;
            user.setName(dto.getName());
            user.setLastname(dto.getLastname());
            user.setCity(dto.getCity());
            user.setPhoneNum(dto.getPhoneNum());
            return new ResponseEntity<>(userDetailsService.update(user), HttpStatus.OK);
        }
    }
}
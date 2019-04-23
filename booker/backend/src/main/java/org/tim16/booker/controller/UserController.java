package org.tim16.booker.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.ProfileDTO;
import org.tim16.booker.model.users.User;
import org.tim16.booker.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User user = service.findOne(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<User> update(@RequestBody ProfileDTO dto) {

        try {
            User user = service.findOne(dto.getId());
            user.setName(dto.getName());
            user.setLastname(dto.getLastname());
            user.setEmail(dto.getEmail());
            user.setAddress(dto.getAddress());
            user.setPhoneNum(dto.getPhoneNum());
            return new ResponseEntity<>(service.update(user), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

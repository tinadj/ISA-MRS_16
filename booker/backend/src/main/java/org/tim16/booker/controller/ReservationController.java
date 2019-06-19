package org.tim16.booker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tim16.booker.comparator.ReservationsOldest;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.users.Reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping(value = "/api/reservations")
public class ReservationController {

    @GetMapping(path = "logged-in-user")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<Reservation>> getLoggedInUserReservations() {
        RegisteredUser user = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Reservation> reservations = new ArrayList<>(user.getReservations());
        //Collections.sort(reservations, new ReservationsOldest());
        //Collections.reverse(reservations);
        
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping(path = "sort/{criteria}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<Reservation>> sort(@PathVariable Integer criteria) {
        RegisteredUser user = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Reservation> reservations = new ArrayList<>(user.getReservations());
        /*
        if (criteria == 0) {
            Collections.sort(reservations, new ReservationsOldest());
        } else if (criteria == 1) {
            Collections.sort(reservations, new ReservationsOldest());
            Collections.reverse(reservations);
        } else {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        */

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}

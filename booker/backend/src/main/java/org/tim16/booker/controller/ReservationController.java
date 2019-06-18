package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.users.Reservation;
import org.tim16.booker.service.ReservationService;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping(path = "logged-in-user")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Set<Reservation>> getLoggedInUserReservations() {
        RegisteredUser user = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user.getReservations(), HttpStatus.OK);
    }
}

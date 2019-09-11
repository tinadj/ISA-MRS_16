package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.FlightReservationDTO;
import org.tim16.booker.dto.RacReservationDTO;
import org.tim16.booker.model.airline.FlightReservation;
import org.tim16.booker.model.rent_a_car.BranchOffice;
import org.tim16.booker.model.rent_a_car.RentACarReservation;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.users.Reservation;
import org.tim16.booker.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/flight-reservations")
public class FlightReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightReservationService flightReservationService;

    @Autowired
    private UserService userService;

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @PostMapping(path = "reserve-flight", consumes="application/json")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> reserveFlight(@RequestBody FlightReservationDTO dto) {
        return reservationService.reserveFlight(dto);
    }
}

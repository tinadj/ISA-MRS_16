package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.HotelReservationDTO;
import org.tim16.booker.model.hotel.HotelReservation;
import org.tim16.booker.model.hotel.Room;
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
@RequestMapping(value = "api/hotel-reservations")
public class HotelReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelReservationService hotelReservationService;

    @Autowired
    private UserService userService;

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @PostMapping(path = "reserve-room", consumes="application/json")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> reserveRoom(@RequestBody HotelReservationDTO dto)
    {
        return reservationService.reserveRoom(dto);
    }

    @PostMapping(path = "cancel/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> cancelRoom(@PathVariable Integer id)
    {
        HotelReservation reservation = hotelReservationService.findOne(id);

        if(reservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Reservation globalReservation = reservationService.findOne(reservation.getReservation().getId());

        if (globalReservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Date todayPlus2 = addDays(new Date(), 2);

        if (!todayPlus2.before(reservation.getCheckinDate()))
            return new ResponseEntity<>(false, HttpStatus.OK);

        globalReservation.setHotelReservation(null);
        reservationService.update(globalReservation);

        hotelReservationService.remove(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /*
    Dodaje broj dana na datum
     */
    private Date addDays(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

}

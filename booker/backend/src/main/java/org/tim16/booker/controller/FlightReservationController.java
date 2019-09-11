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

    @GetMapping(path = "report-daily/{rac}/{strStartDate}/{strEndDate}")
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<List<Integer>> reportDaily(@PathVariable Integer rac, @PathVariable String strStartDate, @PathVariable String strEndDate) {
        List<Integer> result = new ArrayList<>();

        try {
            Date start = new SimpleDateFormat(DATE_FORMAT).parse(strStartDate);
            Date end = new SimpleDateFormat(DATE_FORMAT).parse(strEndDate);
            Integer flightCnt;

            while (!start.after(end)) {
                flightCnt = 0;
                for (FlightReservation reservation : flightReservationService.findAll()) {
                    if (reservation.getFlight().getAirline().getId().equals(rac) && checkIfSameDay(java.sql.Timestamp.valueOf(reservation.getFlight().getArrival()), start)) {
                        flightCnt++;
                    }
                }
                result.add(flightCnt);
                start = addDays(start, 1);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "report-weekly/{rac}/{strStartDate}/{strEndDate}")
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<List<Integer>> reportWeekly(@PathVariable Integer rac, @PathVariable String strStartDate, @PathVariable String strEndDate) {
        List<Integer> result = new ArrayList<>();

        try {
            Date start = new SimpleDateFormat(DATE_FORMAT).parse(strStartDate);
            Date end = new SimpleDateFormat(DATE_FORMAT).parse(strEndDate);
            int weeks = getFullWeeks(start, end);

            Date d2 = addDays(start, 7);
            Integer flightCnt;

            while (weeks > 0) {
                flightCnt = sumFlights(start, d2, rac);
                result.add(flightCnt);
                start = d2;
                d2 = addDays(start, 7);
                weeks--;
            }

            // poslednja nedelja (mozda nije kompletna)
            flightCnt = sumFlights(start, end, rac);
            result.add(flightCnt);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "report-monthly/{rac}/{strStartDate}/{strEndDate}")
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<List<Integer>> reportMonthly(@PathVariable Integer rac, @PathVariable String strStartDate, @PathVariable String strEndDate) {
        List<Integer> result = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        try {
            Date start = new SimpleDateFormat(DATE_FORMAT).parse(strStartDate);
            Date end = new SimpleDateFormat(DATE_FORMAT).parse(strEndDate);
            int months = getMonths(start, end);

            cal.setTime(start);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            Date d2 = getEndDate(month, year);

            if (d2.after(end)) // poslednji mesec
                d2 = end;

            Integer flightCnt = 0;

            while (months >= 0) {
                flightCnt = sumFlights(start, d2, rac);
                result.add(flightCnt);

                start = addDays(d2, 1);

                cal.setTime(start);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);
                d2 = getEndDate(month, year);

                if (d2.after(end)) // poslednji mesec
                    d2 = end;

                months--;
            }
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "report-income/{rac}/{month}/{year}")
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<List<Float>> reportIncome(@PathVariable Integer rac, @PathVariable Integer month, @PathVariable Integer year) {
        List<Float> result = new ArrayList<>();

        Date start = getStartDate(month, year);
        Date end = getEndDate(month, year);


        if (start == null || end == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Float income;
        while (!start.after(end)) {
            income = 0f;
            for (FlightReservation reservation : flightReservationService.findAll()) {
                if (reservation.getFlight().getAirline().getId().equals(rac) && checkIfSameDay(java.sql.Timestamp.valueOf(reservation.getFlight().getArrival()), start)){
                    income += reservation.getTotalPrice();
                }
            }
            result.add(income);
            start = addDays(start, 1);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
    Vraca prvi datum u mesecu
     */
    public Date getStartDate(Integer month, Integer year) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, 1);

        return date.getTime();
    }

    /*
    Vraca poslednji datum u mesecu
     */
    public Date getEndDate(Integer month, Integer year) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, date.getActualMaximum(Calendar.DAY_OF_MONTH));

        return date.getTime();
    }

    private Integer sumFlights(Date start, Date end, Integer rac) {
        Integer flightCnt = 0;

        while (!start.after(end)) {
            for (FlightReservation reservation : flightReservationService.findAll()) {
                if (reservation.getFlight().getAirline().getId().equals(rac) && checkIfSameDay(java.sql.Timestamp.valueOf(reservation.getFlight().getArrival()), start)){
                    flightCnt++;
                }
            }
            start = addDays(start, 1);
        }
        return flightCnt;
    }

    /*
    Racuna broj nedelja izmedju dva datuma
     */
    private int getFullWeeks(Date date1, Date date2){
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        Instant d1i = Instant.ofEpochMilli(c1.getTimeInMillis());
        Instant d2i = Instant.ofEpochMilli(c2.getTimeInMillis());

        LocalDateTime startDate = LocalDateTime.ofInstant(d1i, ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(d2i, ZoneId.systemDefault());

        return (int)ChronoUnit.WEEKS.between(startDate, endDate);
    }

    /*
    Racuna broj meseci izmedju dva datuma
     */
    private int getMonths(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int month1 = cal.get(Calendar.MONTH);
        cal.setTime(date2);
        int month2 = cal.get(Calendar.MONTH);
        return month2 - month1;
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

    /*
    Proverava da li su dva datuma isti dan
     */
    private boolean checkIfSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }
}

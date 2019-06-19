package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.RacReservationDTO;
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
@RequestMapping(value = "/api/rac-reservations")
public class RacReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private RacReservationService racReservationService;

    @Autowired
    private UserService userService;

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @PostMapping(path = "reserve-vehicle", consumes="application/json")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<HttpStatus> reserveVehicle(@RequestBody RacReservationDTO dto) {
        return reservationService.reserveVehicle(dto);
    }

    @PostMapping(path = "cancel/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> reserveVehicle(@PathVariable Integer id) {
        RentACarReservation reservation = racReservationService.findOne(id);

        if (reservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Reservation globalReservation = reservationService.findOne(reservation.getReservation().getId());

        if (globalReservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Date todayPlus2 = addDays(new Date(), 2);
        if (!todayPlus2.before(reservation.getPickUpDate()))
            return new ResponseEntity<>(false, HttpStatus.OK);

        globalReservation.setRentACarReservation(null);
        reservationService.update(globalReservation);

        racReservationService.remove(id);
        return new ResponseEntity<>(true, HttpStatus.OK);

    }



    @GetMapping(path = "report-daily/{rac}/{strStartDate}/{strEndDate}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<List<Integer>> reportDaily(@PathVariable Integer rac, @PathVariable String strStartDate, @PathVariable String strEndDate) {
        List<Integer> result = new ArrayList<>();

        try {
            Date start = new SimpleDateFormat(DATE_FORMAT).parse(strStartDate);
            Date end = new SimpleDateFormat(DATE_FORMAT).parse(strEndDate);
            Integer vehicleCnt;

            while (!start.after(end)) {
                vehicleCnt = 0;
                for (RentACarReservation reservation : racReservationService.findAll()) {
                    if (reservation.getVehicle().getRentACar().getId().equals(rac) && checkIfSameDay(reservation.getPickUpDate(), start)) {
                        vehicleCnt++;
                    }
                }
                result.add(vehicleCnt);
                start = addDays(start, 1);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "report-weekly/{rac}/{strStartDate}/{strEndDate}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<List<Integer>> reportWeekly(@PathVariable Integer rac, @PathVariable String strStartDate, @PathVariable String strEndDate) {
        List<Integer> result = new ArrayList<>();

        try {
            Date start = new SimpleDateFormat(DATE_FORMAT).parse(strStartDate);
            Date end = new SimpleDateFormat(DATE_FORMAT).parse(strEndDate);
            int weeks = getFullWeeks(start, end);

            Date d2 = addDays(start, 7);
            Integer vehicleCnt;

            while (weeks > 0) {
                vehicleCnt = sumVehicles(start, d2, rac);
                result.add(vehicleCnt);
                start = d2;
                d2 = addDays(start, 7);
                weeks--;
            }

            // poslednja nedelja (mozda nije kompletna)
            vehicleCnt = sumVehicles(start, end, rac);
            result.add(vehicleCnt);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "report-monthly/{rac}/{strStartDate}/{strEndDate}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
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

            Integer vehicleCnt = 0;

            while (months >= 0) {
                vehicleCnt = sumVehicles(start, d2, rac);
                result.add(vehicleCnt);

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
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<List<Float>> reportIncome(@PathVariable Integer rac, @PathVariable Integer month, @PathVariable Integer year) {
        List<Float> result = new ArrayList<>();

        Date start = getStartDate(month, year);
        Date end = getEndDate(month, year);


        if (start == null || end == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Float income;
        while (!start.after(end)) {
            income = 0f;
            for (RentACarReservation reservation : racReservationService.findAll()) {
                if (reservation.getVehicle().getRentACar().getId().equals(rac) && checkIfSameDay(reservation.getPickUpDate(), start)) {
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

    /*
    Sumira broj iznajmljenih vozila svakog dana
     */
    private Integer sumVehicles(Date start, Date end, Integer rac) {
        Integer vehicleCnt = 0;

        while (!start.after(end)) {
            for (RentACarReservation reservation : racReservationService.findAll()) {
                if (reservation.getVehicle().getRentACar().getId().equals(rac) && checkIfSameDay(reservation.getPickUpDate(), start)) {
                    vehicleCnt++;
                }
            }
            start = addDays(start, 1);
        }
        return vehicleCnt;
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

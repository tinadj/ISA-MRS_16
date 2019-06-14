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

    @RequestMapping(value = "reserve-vehicle", method = RequestMethod.POST, consumes="application/json")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<HttpStatus> reserveVehicle(@RequestBody RacReservationDTO dto) {
        Reservation reservation = new Reservation();
        RegisteredUser user = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reservation.setUser(user);

        RentACarReservation rentACarReservation = setUpRACReservation(dto);
        if (rentACarReservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        user.addReservations(reservation);

        racReservationService.create(rentACarReservation);
        rentACarReservation.setReservation(reservation);
        reservation.setRentACarReservation(rentACarReservation);

        reservationService.update(reservation);
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    // Incijalizacija rent a car rezervacije
    private RentACarReservation setUpRACReservation(RacReservationDTO dto) {
        RentACarReservation rentACarReservation = new RentACarReservation();

        Vehicle vehicle = vehicleService.findOne(dto.getVehicle());
        BranchOffice pickUp = branchOfficeService.findOne(dto.getPickUpLocation());
        BranchOffice dropOff = branchOfficeService.findOne(dto.getDropOffLocation());

        if (vehicle == null || pickUp == null || dropOff == null)
            return null;

        rentACarReservation.setVehicle(vehicle);
        rentACarReservation.setPickUpDate(dto.getPickUpDate());
        rentACarReservation.setDays(dto.getDays());
        rentACarReservation.setPassangerNum(dto.getPassangerNum());
        rentACarReservation.setPickUpLocation(pickUp);
        rentACarReservation.setDropOffLocation(dropOff);

        Float price = vehicle.getPrice() * dto.getDays();
        if (vehicle.getDiscount() != 0) {
            price = price - (price / 100 * vehicle.getDiscount());
        }
        rentACarReservation.setTotalPrice(price);

        return rentACarReservation;
    }

    @RequestMapping(value = "report-daily/{rac}/{strStartDate}/{strEndDate}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<List<Integer>> reportDaily(@PathVariable Integer rac, @PathVariable String strStartDate, @PathVariable String strEndDate) {
        List<Integer> result = new ArrayList<>();

        try {
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(strStartDate);
            Date end = new SimpleDateFormat("yyyy-MM-dd").parse(strEndDate);
            Integer vehicleCnt;

            while (start.before(end)) {
                vehicleCnt = 0;
                for (RentACarReservation reservation : racReservationService.findAll()) {
                    if (reservation.getVehicle().getRentACar().getId() == rac) {
                        if (reservation.getPickUpDate().equals(start))
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

    @RequestMapping(value = "report-weekly/{rac}/{strStartDate}/{strEndDate}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<List<Integer>> reportWeekly(@PathVariable Integer rac, @PathVariable String strStartDate, @PathVariable String strEndDate) {
        List<Integer> result = new ArrayList<>();

        try {
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(strStartDate);
            Date end = new SimpleDateFormat("yyyy-MM-dd").parse(strEndDate);
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

    @RequestMapping(value = "report-income/{rac}/{month}/{year}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<List<Float>> reportIncome(@PathVariable Integer rac, @PathVariable Integer month, @PathVariable Integer year) {
        List<Float> result = new ArrayList<>();

        Date start = getStartDate(month, year);
        Date end = getEndDate(month, year);

        if (start == null || end == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Float income;
        while (start.before(end)) {
            income = 0f;
            for (RentACarReservation reservation : racReservationService.findAll()) {
                if (reservation.getVehicle().getRentACar().getId() == rac) {
                    if (reservation.getPickUpDate().equals(start))
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

        while (start.before(end)) {
            for (RentACarReservation reservation : racReservationService.findAll()) {
                if (reservation.getVehicle().getRentACar().getId() == rac) {
                    if (reservation.getPickUpDate().equals(start))
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
    Dodaja broj dana na datum
     */
    private Date addDays(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }




}

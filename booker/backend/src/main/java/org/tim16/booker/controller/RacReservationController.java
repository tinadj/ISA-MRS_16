package org.tim16.booker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.tim16.booker.dto.RacReservationDTO;
import org.tim16.booker.model.rent_a_car.BranchOffice;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.rent_a_car.RentACarReservation;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.users.Reservation;
import org.tim16.booker.service.*;

@RestController
@RequestMapping(value = "/api/rac-reservations")
public class RacReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RacReservationService racReservationService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/reserve-vehicle", method = RequestMethod.POST, consumes="application/json")
    @PreAuthorize("hasAuthority('USER')")
    private ResponseEntity<HttpStatus> reserveVehicle(@RequestBody RacReservationDTO dto) {
        Reservation reservation = new Reservation();
        RegisteredUser user = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        RentACarReservation rentACarReservation = setUpRacReservtion(dto);
        if (rentACarReservation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        reservation.setRentACarReservation(rentACarReservation);
        user.getReservations().add(reservation);

        racReservationService.create(rentACarReservation);
        reservationService.update(reservation);
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private RentACarReservation setUpRacReservtion(RacReservationDTO dto) {
        RentACarReservation reservation = new RentACarReservation();
        Vehicle vehicle = vehicleService.findOne(dto.getVehicle());
        BranchOffice pickUp = branchOfficeService.findOne(dto.getPickUpLocation());
        BranchOffice dropOff = branchOfficeService.findOne(dto.getDropOffLocation());

        if (vehicle == null || pickUp == null || dropOff == null)
            return null;

        reservation.setVehicle(vehicle);
        reservation.setPickUpDate(dto.getPickUpDate());
        reservation.setDays(dto.getDays());
        reservation.setPassangerNum(dto.getPassangerNum());
        reservation.setPickUpLocation(pickUp);
        reservation.setDropOffLocation(dropOff);

        Float price = vehicle.getPrice() * dto.getDays();
        if (vehicle.getDiscount() != 0) {
            price = price - (price / 100 * vehicle.getDiscount());
        }

        reservation.setTotalPrice(price);
        return reservation;
    }
}

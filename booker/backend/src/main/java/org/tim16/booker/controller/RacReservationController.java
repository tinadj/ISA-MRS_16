package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tim16.booker.dto.RacReservationDTO;
import org.tim16.booker.model.rent_a_car.BranchOffice;
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


}

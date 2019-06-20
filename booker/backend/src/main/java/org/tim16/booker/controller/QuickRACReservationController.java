package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.QuickRAC_DTO;
import org.tim16.booker.dto.RentACarDTO;
import org.tim16.booker.model.rent_a_car.QuickRACReservation;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.rent_a_car.RentACarReservation;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.service.QuickRACReservationService;
import org.tim16.booker.service.RacReservationService;
import org.tim16.booker.service.VehicleService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/rent-a-car-quick-reservations")
public class QuickRACReservationController {

    @Autowired
    private QuickRACReservationService quickRACReservationService;
    @Autowired
    private RacReservationService reservationService;
    @Autowired
    private VehicleService vehicleService;

    @GetMapping(path = "/get/{id}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<List<QuickRACReservation>> getQuickReservation(@PathVariable Integer id) {
        List<QuickRACReservation> result = new ArrayList<>();

        for (QuickRACReservation quickRACReservation : quickRACReservationService.findAll()) {
            if (quickRACReservation.getVehicle().getRentACar().getId().equals(id)) {
                result.add(quickRACReservation);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(path = "/add", consumes="application/json")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<HttpStatus> add(@RequestBody QuickRAC_DTO dto) {
        try {
            Vehicle vehicle = vehicleService.findOne(dto.getVehicleID());
            if (vehicle == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            if (!isVehicleReserved(vehicle.getId(), dto.getPickUpDate(), dto.getDropOffDate()) && !isDiscountDefined(vehicle.getId(), dto.getPickUpDate(), dto.getDropOffDate())) {
                QuickRACReservation quickReservation = new QuickRACReservation();
                quickReservation.setVehicle(vehicle);
                quickReservation.setPickUpDate(dto.getPickUpDate());
                quickReservation.setDropOffDate(dto.getDropOffDate());
                quickReservation.setDiscount(dto.getDiscount());

                quickRACReservationService.create(quickReservation);

                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

        } catch (EntityNotFoundException ex ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/remove/{id}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        try {
            QuickRACReservation reservation = quickRACReservationService.findOne(id);
            if (reservation == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            if (!isVehicleReserved(reservation.getVehicle().getId(), reservation.getPickUpDate(), reservation.getDropOffDate())) {
                quickRACReservationService.remove(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isVehicleReserved(Integer id, Date pickUp, Date dropOff) {
        for (RentACarReservation reservation : reservationService.findAll()) {
            if (reservation.getVehicle().getId().equals(id)) {
                Date returnDateRes = RentACarController.calculateReturnDate(reservation.getPickUpDate(), reservation.getDays());
                if (reservation.getPickUpDate().before(dropOff) && returnDateRes.after(pickUp)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isDiscountDefined(Integer id, Date pickUp, Date dropOff) {
        for (QuickRACReservation reservation : quickRACReservationService.findAll()) {
            if (reservation.getVehicle().getId().equals(id)) {
                if (reservation.getPickUpDate().before(dropOff) && reservation.getDropOffDate().after(pickUp)) {
                    return true;
                }
            }
        }
        return false;
    }

}

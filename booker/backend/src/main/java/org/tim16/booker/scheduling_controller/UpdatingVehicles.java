package org.tim16.booker.scheduling_controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.tim16.booker.model.rent_a_car.RentACarReservation;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.service.RacReservationService;
import org.tim16.booker.service.VehicleService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class UpdatingVehicles {

    @Autowired
    private RacReservationService reservationService;
    @Autowired
    private VehicleService vehicleService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(
            cron = "${greeting.cron}")
    public void cronJob() {
        logger.info("> updating vehicles currently in branch office");

        // prebacivanje vozila iz pick up branch office-a u drop off
        List<RentACarReservation> reservations = reservationService.findAll();
        Date today = new Date();
        for (RentACarReservation reservation : reservations) {
            Date returnDate = calculateReturnDate(reservation);
            if (returnDate.before(today) && !reservation.getPickUpLocation().getId().equals(reservation.getDropOffLocation().getId())) {
                Vehicle vehicle = reservation.getVehicle();
                vehicle.setCurrentlyIn(reservation.getDropOffLocation());
                vehicleService.update(vehicle);
            }
        }
        logger.info("< updated vehicles currently in branch office");
    }

    /*
    Izracunava datum povratka vozila na osnovu datuma preuzimanja i broja dana
     */
    private Date calculateReturnDate(RentACarReservation reservation) {
        Calendar c = Calendar.getInstance();
        c.setTime(reservation.getPickUpDate());
        c.add(Calendar.DATE, reservation.getDays());
        return c.getTime();
    }
}

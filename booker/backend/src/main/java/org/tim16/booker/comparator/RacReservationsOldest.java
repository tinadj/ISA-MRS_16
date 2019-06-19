package org.tim16.booker.comparator;

import org.tim16.booker.model.rent_a_car.RentACarReservation;

import java.util.Comparator;

public class RacReservationsOldest implements Comparator<RentACarReservation> {

    @Override
    public int compare(RentACarReservation o1, RentACarReservation o2) {
        return o1.getPickUpDate().compareTo(o2.getPickUpDate());
    }
}

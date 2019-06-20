package org.tim16.booker.comparator;

import org.tim16.booker.model.users.Reservation;

import java.util.Comparator;

public class ReservationsOldest implements Comparator<Reservation> {

    @Override
    public int compare(Reservation o1, Reservation o2) {
        return o1.getRentACarReservation().getPickUpDate().compareTo(o2.getRentACarReservation().getPickUpDate());
    }
}

package org.tim16.booker.comparator;

import org.tim16.booker.model.rent_a_car.RentACar;

import java.util.Comparator;

public class RentACarState implements Comparator<RentACar> {

    @Override
    public int compare(RentACar o1, RentACar o2) {
        return o1.getAddress().getState().compareTo(o2.getAddress().getState());
    }
}

package org.tim16.booker.comparator;

import org.tim16.booker.model.rent_a_car.Vehicle;

import java.util.Comparator;

public class VehiclePrice implements Comparator<Vehicle> {

    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return o1.getPrice().compareTo(o2.getPrice());
    }
}

package org.tim16.booker.service.interfaces;

import org.tim16.booker.model.rent_a_car.Vehicle;

import java.util.List;

public interface IVehicleService {

    public Vehicle findOne(Integer id);
    public List<Vehicle> findAll();
    public Vehicle create(Vehicle vehicle);
    public Vehicle update(Vehicle vehicle);
    public void remove(Integer id);
}

package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.repository.VehicleRepository;
import org.tim16.booker.service.interfaces.IVehicleService;

import java.util.List;

@Service
public class VehicleService implements IVehicleService {

    @Autowired
    private VehicleRepository repository;

    public Vehicle findOne(Integer id) {
        return repository.getOne(id);
    }
    public List<Vehicle> findAll() {
        return repository.findAll();
    }
    public Vehicle create(Vehicle vehicle) {
        return repository.save(vehicle);
    }
    public Vehicle update(Vehicle vehicle) {
        return repository.save(vehicle);
    }
    public void remove(Integer id) {
        repository.deleteById(id);
    }
}

package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.repository.VehicleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class VehicleService  {

    @Autowired
    private VehicleRepository repository;

    public Vehicle findOne(Integer id) {
        return repository.getOne(id);
    }

    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = false)
    public Vehicle create(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Vehicle update(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void remove(Integer id) {
        repository.deleteById(id);
    }
}

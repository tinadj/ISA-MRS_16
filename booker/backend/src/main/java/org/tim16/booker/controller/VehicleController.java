package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.VehicleDTO;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.service.RentACarService;
import org.tim16.booker.service.VehicleService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private RentACarService rentACarService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getAll() {
        return new ResponseEntity<>(vehicleService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Vehicle> add(@RequestBody VehicleDTO dto) {
        // a new vehicle must have rent a car defined
        if (dto.getRentACar() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RentACar rentACar = rentACarService.findOne(dto.getRentACar().getId());

        if (rentACar.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setName(dto.getName());
        vehicle.setBrand(dto.getBrand());
        vehicle.setDescription(dto.getDescription());
        vehicle.setModel(dto.getModel());
        vehicle.setProductionYear(dto.getProductionYear());
        vehicle.setSeatsNum(dto.getSeatsNum());
        rentACar.addVehicle(vehicle);
        vehicle.setRentACar(rentACar);

        vehicle = vehicleService.create(vehicle);
        rentACarService.update(rentACar);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Integer id)
    {
        Vehicle vehicle = vehicleService.findOne(id);

        if (vehicle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<List<Vehicle>> removeVehicle(@PathVariable Integer id)
    {
        Vehicle vehicle = vehicleService.findOne(id);

        if (vehicle == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RentACar rentACar = rentACarService.findOne(vehicle.getRentACar().getId());
        rentACar.removeVehicle(vehicle.getId());

        rentACarService.update(rentACar);
        vehicleService.remove(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method =  RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody VehicleDTO dto)
    {
        try
        {
            Vehicle vehicle = vehicleService.findOne(dto.getId());

            vehicle.setName(dto.getName());
            vehicle.setBrand(dto.getBrand());
            vehicle.setModel(dto.getModel());
            vehicle.setProductionYear(dto.getProductionYear());
            vehicle.setDescription(dto.getDescription());
            RentACar rentACar = rentACarService.findOne(dto.getRentACar().getId());
            vehicle.setRentACar(rentACar);

            rentACar.removeVehicle(dto.getId());
            rentACar.addVehicle(vehicle);
            rentACarService.update(rentACar);

            return new ResponseEntity<>(vehicleService.update(vehicle), HttpStatus.OK);

        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

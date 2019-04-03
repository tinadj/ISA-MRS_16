package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tim16.booker.dto.VehicleDTO;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.service.RentACarService;
import org.tim16.booker.service.VehicleService;

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

        if (rentACar == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setName(dto.getName());
        vehicle.setBrand(dto.getBrand());
        vehicle.setDescription(dto.getDescription());
        vehicle.setModel(dto.getModel());
        vehicle.setProductionYear(dto.getProductionYear());
        vehicle.setSeatsNum(dto.getSeatsNum());
        vehicle.setRentACar(rentACar);

        vehicle = vehicleService.create(vehicle);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }


}

package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.RentACarDTO;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.service.RentACarService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/rent_a_cars")
public class RentACarController {

    @Autowired
    private RentACarService service;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<RentACar>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<RentACar> add(@RequestBody RentACarDTO dto) {
        RentACar rentACar = new RentACar();
        rentACar.setName(dto.getName());

        try {
            rentACar = service.create(rentACar);
            return new ResponseEntity<>(rentACar, HttpStatus.CREATED);
        } catch(Exception e)
        {   // catches duplicate name
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RentACar> getRentACar(@PathVariable Integer id) {
        RentACar rentACar = service.findOne(id);

        if (rentACar == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rentACar, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<RentACar> update(@RequestBody RentACarDTO dto) {

        try {
            RentACar rentACar = service.findOne(dto.getId());

            rentACar.setName(dto.getName());
            return new ResponseEntity<>(service.update(rentACar), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

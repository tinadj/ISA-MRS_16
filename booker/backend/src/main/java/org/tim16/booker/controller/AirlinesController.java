package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.AirlineDTO;
import org.tim16.booker.model.airline.Airline;
import org.tim16.booker.service.AirlineService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/airlines")
public class AirlinesController {

    @Autowired
    private AirlineService service;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Airline>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Airline> add(@RequestBody AirlineDTO dto) {
        Airline airline = new Airline();
        airline.setName(dto.getName());
        airline.setDescription(dto.getDescription());

        try {
            airline = service.create(airline);
            return new ResponseEntity<>(airline, HttpStatus.CREATED);
        } catch(Exception e)
        {   // catches duplicate name
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Airline> getAirline(@PathVariable Integer id) {
        Airline airline = service.findOne(id);

        if (airline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(airline, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Airline> update(@RequestBody AirlineDTO dto) {

        try {
            Airline airline = service.findOne(dto.getId());

            airline.setName(dto.getName());
            airline.setDescription(dto.getDescription());
            return new ResponseEntity<>(service.update(airline), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.AirlineDTO;
import org.tim16.booker.model.airline.Airline;
import org.tim16.booker.model.airline.Flight;

import org.tim16.booker.model.airline.LuggagePrice;
import org.tim16.booker.model.airline.LuggageType;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.service.AirlineService;
import org.tim16.booker.service.DestinationService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/airlines")
public class AirlinesController {

    @Autowired
    private AirlineService service;
    @Autowired
    private DestinationService destinationService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Airline>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/add", consumes = "application/json")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Airline> add(@RequestBody AirlineDTO dto) {
        Airline airline = new Airline();
        airline.setName(dto.getName());
        airline.setDescription(dto.getDescription());
        airline.setLatitude(dto.getLatitude());
        airline.setLongitude(dto.getLongitude());

        Destination destination = destinationService.findByCityAndState(dto.getAddress().getCity(), dto.getAddress().getCity());

        if (destination == null) {
            destination = new Destination();
            destination.setCity(dto.getAddress().getCity());
            destination.setState(dto.getAddress().getState());
            destinationService.create(destination);
        }
        airline.setAddress(destination);

        LuggagePrice co = new LuggagePrice();
        co.setType(LuggageType.CARRY_ON);
        co.setPrice((float)0);
        airline.getLuggagePrices().add(co);

        LuggagePrice c = new LuggagePrice();
        c.setType(LuggageType.CHECKED);
        c.setPrice((float)0);
        airline.getLuggagePrices().add(c);

        try {
            airline = service.create(airline);
            return new ResponseEntity<>(airline, HttpStatus.CREATED);
        } catch(Exception e)
        {   // catches duplicate name
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Airline> getAirline(@PathVariable Integer id) {
        Airline airline = service.findOne(id);

        if (airline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(airline, HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Airline> update(@RequestBody AirlineDTO dto) {

        try {
            Airline airline = service.findOne(dto.getId());
            airline.setName(dto.getName());
            airline.setDescription(dto.getDescription());
            airline.setLatitude(dto.getLatitude());
            airline.setLongitude(dto.getLongitude());

            Destination destination = destinationService.findByCityAndState(dto.getAddress().getCity(), dto.getAddress().getCity());

            if (destination == null) {
                destination = new Destination();
                destination.setCity(dto.getAddress().getCity());
                destination.setState(dto.getAddress().getState());
                destinationService.create(destination);
            }
            airline.setAddress(destination);

            return new ResponseEntity<>(service.update(airline), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}/destinations", method = RequestMethod.GET)
    public ResponseEntity<List<Destination>> getDestinations(@PathVariable Integer id) {
        Airline airline = service.findOne(id);

        if (airline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Destination> destinations = new ArrayList<>();
        for (Destination d: airline.getDestinations()) {
            destinations.add(d);
        }

        return new ResponseEntity<>(destinations, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/flights", method = RequestMethod.GET)
    public ResponseEntity<List<Flight>> getFlights(@PathVariable Integer id) {
        Airline airline = service.findOne(id);

        if (airline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Flight> flights = new ArrayList<>();
        for (Flight f: airline.getFlights()) {
            flights.add(f);
        }

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

}
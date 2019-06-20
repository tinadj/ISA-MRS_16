package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.AirlineDTO;
import org.tim16.booker.dto.LuggageDTO;
import org.tim16.booker.model.airline.*;


import org.tim16.booker.model.airline.LuggagePrice;
import org.tim16.booker.model.airline.LuggageType;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.model.utility.Rate;
import org.tim16.booker.service.AirlineService;
import org.tim16.booker.service.DestinationService;
import org.tim16.booker.service.RateService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/airlines")
public class AirlinesController {

    @Autowired
    private AirlineService service;
    @Autowired
    private DestinationService destinationService;
    @Autowired
    private RateService rateService;

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

    @PutMapping(path = "/edit-prices")
    public ResponseEntity<Airline> update(@RequestBody LuggageDTO dto) {

        try {
            Airline airline = service.findOne(dto.getAirlineId());

            for (LuggagePrice lp : airline.getLuggagePrices()
                 ) {
                if(lp.getType() == LuggageType.CARRY_ON) {
                    lp.setPrice(dto.getCarryOnPrice());
                }else {
                    lp.setPrice(dto.getCheckedPrice());
                }
            }

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

    @RequestMapping(value = "/{id}/tickets", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getTickets(@PathVariable Integer id) {
        Airline airline = service.findOne(id);

        if (airline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Ticket> tickets = new ArrayList<>();
        for (Ticket d: airline.getDiscountTickets()) {
            tickets.add(d);
        }

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(value = "/ticket/{id}/{airline}", method = RequestMethod.GET)
    public ResponseEntity<Flight> findTicket(@PathVariable Integer id, @PathVariable Integer airline)
    {
        Airline air = service.findOne(airline);
        Set<Flight> flights = air.getFlights();

        for(Flight f : flights) {
            if(f.findTicket(id) != null) {
                return new ResponseEntity<>(f, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    /*
    Dobavljanje ocene aviokompanije
     */
    @GetMapping(path = "rating/{name}")
    public ResponseEntity<Float> getRating(@PathVariable String name) {
        Airline rentACar = service.findByName(name);

        if (rentACar == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(RentACarController.getAverageRating(rentACar.getRating()), HttpStatus.OK);
    }

    /*
    Ocenjivanje aviokompanije
     */
    @PostMapping(path = "rate/{name}/{rateValue}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Float> rateVehicle(@PathVariable String name, @PathVariable Integer rateValue) {
        Airline airline = service.findByName(name);
        RegisteredUser user = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (airline == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // Ako je korisnik vec ocenio, promeni vrednost te ocene
        boolean rated = false;
        for (Rate rate : airline.getRating()) {
            if (rate.getUser().getId().equals(user.getId())) {
                rate.setRateValue(rateValue);
                rateService.update(rate);
                rated = true;
                break;
            }
        }

        // Ako nije ocenio, dodaj novu ocenu
        if (!rated) {
            Rate rate = new Rate(user, rateValue);
            airline.getRating().add(rate);
            rateService.create(rate);
            service.update(airline);
        }

        return new ResponseEntity<>(RentACarController.getAverageRating(airline.getRating()), HttpStatus.OK);
    }

}
package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.comparator.*;
import org.tim16.booker.dto.AirlineDestinationDTO;
import org.tim16.booker.dto.DestinationDTO;
import org.tim16.booker.model.airline.*;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.service.AirlineService;
import org.tim16.booker.service.DestinationService;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RestController
@RequestMapping(value = "/api/destinations")

public class DestinationController {
    @Autowired
    private DestinationService destinationService;

    @Autowired
    private AirlineService airlineService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Destination>> getAll() {
        return new ResponseEntity<>(destinationService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<Destination> add(@RequestBody AirlineDestinationDTO dto) {
        // a new destination must have airline defined

        Airline airline = airlineService.findOne(dto.getAirlineId());
        if (airline == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Destination destination = new Destination();
        destination.setCity(dto.getCity());
        destination.setState(dto.getState());
        airline.getDestinations().add(destination);

        destination = destinationService.create(destination);
        airlineService.update(airline);
        return new ResponseEntity<>(destination, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/remove/{id}/{airlineID}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<List<Destination>> removeDestination(@PathVariable Integer id, @PathVariable Integer airlineID)
    {
        Destination destination = destinationService.findOne(id);

        if (destination == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Airline airline = airlineService.findOne(airlineID);
        airline.removeDestination(id);

        airlineService.update(airline);
        destinationService.remove(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

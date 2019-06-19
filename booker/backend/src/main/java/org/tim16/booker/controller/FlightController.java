package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.comparator.*;
import org.tim16.booker.dto.AirlineDestinationDTO;
import org.tim16.booker.dto.DestinationDTO;
import org.tim16.booker.dto.FlightDTO;
import org.tim16.booker.model.airline.*;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.service.AirlineService;
import org.tim16.booker.service.DestinationService;
import org.tim16.booker.service.FlightService;
import org.tim16.booker.service.SeatService;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RestController
@RequestMapping(value = "/api/flights")

public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private AirlineService airlineService;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Flight>> getAll() {
        return new ResponseEntity<>(flightService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<Flight> add(@RequestBody FlightDTO dto) {
        Airline airline = airlineService.findOne(5);
        if (airline == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Flight flight = new Flight();
        flight.setAirline(airline);

        Destination dep = destinationService.findOne(dto.getDeparture());
        if (dep == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        flight.setDepartureDestination(dep);

        dep = destinationService.findOne(dto.getArrival());
        if (dep == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        flight.setArrivalDestination(dep);
        flight.setArrival(dto.getArrivalTime());
        flight.setDeparture(dto.getDepartureTime());
        flight.setTransferNum(dto.getTransferNumber());

        Seat seat;
        Ticket ticket;
        int rowNum = 1;

        for(int i = 0; i <dto.getFirstClass(); i++){
            if((i+1)%4 == 0)
                rowNum++;

            seat = new Seat();
            seat.setSeatRow(rowNum);
            char letter = (char) ('A' + i%4);
            seat.setSeatLetter(String.valueOf(letter));
            seat.setType(TravelClass.FIRST);
            flight.getSeats().add(seat);

            ticket = new Ticket();
            ticket.setFlight(flight);
            ticket.setDiscount(0);
            ticket.setPrice(dto.getFirstClassPrice());
            ticket.setReserved(false);
            ticket.setSeat(seat);

            flight.getTickets().add(ticket);

        }

        for(int i = 0; i <dto.getBusinessClass(); i++){
            if((i+1)%4 == 0)
                rowNum++;

            seat = new Seat();
            seat.setSeatRow(rowNum);
            char letter = (char) ('A' + i%4);
            seat.setSeatLetter(String.valueOf(letter));
            seat.setType(TravelClass.BUSINESS);
            flight.getSeats().add(seat);

            ticket = new Ticket();
            ticket.setFlight(flight);
            ticket.setDiscount(0);
            ticket.setPrice(dto.getBusinessClassPrice());
            ticket.setReserved(false);
            ticket.setSeat(seat);

            flight.getTickets().add(ticket);
        }

        for(int i = 0; i <dto.getEconomyClass(); i++){
            if((i+1)%4 == 0)
                rowNum++;

            seat = new Seat();
            seat.setSeatRow(rowNum);
            char letter = (char) ('A' + i%4);
            seat.setSeatLetter(String.valueOf(letter));
            seat.setType(TravelClass.ECONOMY);
            flight.getSeats().add(seat);

            ticket = new Ticket();
            ticket.setFlight(flight);
            ticket.setDiscount(0);
            ticket.setPrice(dto.getEconomyClassPrice());
            ticket.setReserved(false);
            ticket.setSeat(seat);

            flight.getTickets().add(ticket);

        }

        TicketPrice tpF = new TicketPrice();
        tpF.setPrice(dto.getFirstClassPrice());
        tpF.setTravelClass(TravelClass.FIRST);

        flight.getTicketPrices().add(tpF);

        TicketPrice tpE = new TicketPrice();
        tpE.setPrice(dto.getEconomyClassPrice());
        tpE.setTravelClass(TravelClass.ECONOMY);

        flight.getTicketPrices().add(tpE);

        TicketPrice tpB = new TicketPrice();
        tpB.setPrice(dto.getBusinessClassPrice());
        tpB.setTravelClass(TravelClass.BUSINESS);

        flight.getTicketPrices().add(tpB);

        airline.getFlights().add(flight);

        flight = flightService.create(flight);
        airlineService.update(airline);
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }
}

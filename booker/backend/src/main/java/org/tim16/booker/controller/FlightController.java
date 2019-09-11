package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.comparator.*;

import org.tim16.booker.dto.*;

import org.tim16.booker.model.airline.*;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.model.utility.Rate;
import org.tim16.booker.service.*;

import javax.persistence.EntityNotFoundException;
import java.time.ZoneId;
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

    @Autowired
    private RateService rateService;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Flight>> getAll() {
        return new ResponseEntity<>(flightService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<Flight> add(@RequestBody FlightDTO dto) {

        Airline airline = airlineService.findOne(dto.getId());

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

    @RequestMapping(value = "/add-seat", method = RequestMethod.POST, consumes="application/json")
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<Seat> add(@RequestBody SeatDTO dto) {
        Flight flight = flightService.findOne(dto.getId());
        if (flight == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for(Seat s : flight.getSeats()) {
            if(s.getSeatLetter().equals(dto.getSeatLetter()) && s.getSeatRow() == dto.getSeatRow()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        Ticket ticket;
        Seat seat = new Seat();
        seat.setSeatRow(dto.getSeatRow());
        seat.setSeatLetter(dto.getSeatLetter());

        ticket = new Ticket();
        ticket.setFlight(flight);
        ticket.setDiscount(0);

        if(dto.getType().equals("BUSINESS")) {
            seat.setType(TravelClass.BUSINESS);
        }else if(dto.getType().equals("FIRST")) {
            seat.setType(TravelClass.FIRST);
        } else {
            seat.setType(TravelClass.ECONOMY);
        }
        ticket.setPrice(0.0f);
        flight.getSeats().add(seat);
        ticket.setReserved(false);
        ticket.setSeat(seat);

        flight.getTickets().add(ticket);

        flight = flightService.update(flight);
        return new ResponseEntity<>(seat, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/add-discount", method = RequestMethod.POST, consumes="application/json")
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<Ticket> addDiscount(@RequestBody TicketDTO dto) {
        Flight flight = flightService.findOne(dto.getId());
        if (flight == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(dto.getDiscount() < 0 || dto.getDiscount() >99) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ticket ticket = null;

        for(Ticket s : flight.getTickets()) {
            if(s.getId() == dto.getTicket()){
                ticket = s;
                break;
            }
        }

        if(ticket == null || ticket.getReserved() == true) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        flight.getTickets().remove(ticket);
        ticket.setReserved(true);
        ticket.setFlight(flight);
        ticket.setDiscount(dto.getDiscount());

        for(TicketPrice tp : flight.getTicketPrices()) {
            if(tp.getTravelClass() == ticket.getSeat().getType()) {
                ticket.setPrice(tp.getPrice());
            }
        }

        flight.getTickets().add(ticket);
        flight = flightService.update(flight);

        Airline airline = airlineService.findOne(flight.getAirline().getId());
        airline.getDiscountTickets().add(ticket);
        airline = airlineService.update(airline);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/remove/{id}/{airlineID}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<List<Flight>> removeFlight(@PathVariable Integer id, @PathVariable Integer airlineID)
    {
        Flight flight = flightService.findOne(id);

        if (flight == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Airline airline = airlineService.findOne(airlineID);
        airline.removeFlight(id);

        airlineService.update(airline);
        flightService.remove(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/removeSeat/{SeatId}/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('AIRLINE_ADMIN')")
    public ResponseEntity<List<Flight>> removeSeat(@PathVariable Integer SeatId, @PathVariable Integer id)
    {
        Flight flight = flightService.findOne(id);

        if (flight == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for (Ticket t : flight.getTickets()) {
            if(t.getSeat().getId() == SeatId) {
                if(t.getReserved() == true) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

            flight.getTickets().remove(t);
            break;
        }

        for (Seat s : flight.getSeats()) {
            if(s.getId() == SeatId) {
                flight.getSeats().remove(s);
                break;
            }
        }


        flightService.update(flight);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    private List<Flight> searchByDeparture(List<Flight> flights, List<Flight> result, String name) {
        for (Flight flight: flights) {
            if (!flight.getDepartureDestination().getCity().toLowerCase().contains(name.toLowerCase())) {
                result.remove(flight);
            }

            if (!flight.getDepartureDestination().getState().toLowerCase().contains(name.toLowerCase())) {
                result.remove(flight);
            }
        }
        return result;
    }

    private List<Flight> searchByDepartureDate(List<Flight> flights, List<Flight> result, Date date) {
        for (Flight flight: flights) {
            if (!flight.getDeparture().toLocalDate().equals(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                result.remove(flight);
            }
        }
        return result;
    }

    private List<Flight> searchByReturnDate(String arrival,List<Flight> result, Date date) {
        for (Flight flight: flightService.findAll()) {
            if(flight.getDepartureDestination().getCity().toLowerCase().contains(arrival.toLowerCase()) || flight.getDepartureDestination().getState().toLowerCase().contains(arrival.toLowerCase())) {
                if (!flight.getDeparture().toLocalDate().equals(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                    result.add(flight);
                }
            }
        }
        return result;
    }


    private List<Flight> searchByArrival(List<Flight> flights, List<Flight> result, String name) {
        for (Flight flight: flights) {
            if (!flight.getArrivalDestination().getCity().toLowerCase().contains(name.toLowerCase())) {
                result.remove(flight);
            }

            if (!flight.getArrivalDestination().getState().toLowerCase().contains(name.toLowerCase())) {
                result.remove(flight);
            }
        }
        return result;
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<Flight>> search(@RequestBody FlightSearchParam dto) {
        List<Flight> rentACars = flightService.findAll();
        List<Flight> result = flightService.findAll();
        if (!dto.getDeparture().equals("")) {
            result = searchByDeparture(rentACars, result, dto.getDeparture());
        }

        if (!dto.getArrival().equals("")) {
            result = searchByArrival(rentACars, result, dto.getArrival());
        }

        if (dto.getDepartureDate() != null) {
            result = searchByDepartureDate(rentACars, result, dto.getDepartureDate());
        }

        if (dto.getReturnDate() != null) {
            result = searchByReturnDate(dto.getArrival(), result, dto.getDepartureDate());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
    Dobavljanje ocene leta
     */
    @GetMapping(path = "rating/{id}")
    public ResponseEntity<Float> getRating(@PathVariable Integer id) {
        Flight flight = flightService.findOne(id);

        if (flight == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(RentACarController.getAverageRating(flight.getRating()), HttpStatus.OK);
    }

    /*
    Ocenjivanje leta
     */
    @PostMapping(path = "rate/{id}/{rateValue}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Float> rateVehicle(@PathVariable Integer id, @PathVariable Integer rateValue) {
        Flight flight = flightService.findOne(id);
        RegisteredUser user = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (flight == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // Ako je korisnik vec ocenio, promeni vrednost te ocene
        boolean rated = false;
        for (Rate rate : flight.getRating()) {
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
            flight.getRating().add(rate);
            rateService.create(rate);
            flightService.update(flight);
        }

        return new ResponseEntity<>(RentACarController.getAverageRating(flight.getRating()), HttpStatus.OK);
    }
}

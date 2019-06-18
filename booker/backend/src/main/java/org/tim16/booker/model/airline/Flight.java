package org.tim16.booker.model.airline;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.model.utility.Rate;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonManagedReference("airline-flight")
    @ManyToOne
    @JoinColumn(name = "airline", referencedColumnName = "id", nullable = false)
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "departureDestination", referencedColumnName = "id", nullable = false)
    private Destination departureDestination;

    @ManyToOne
    @JoinColumn(name = "arrivalDestination", referencedColumnName = "id", nullable = false)
    private Destination arrivalDestination;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date departure;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date arrival;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private int transferNum;

    // lokacije presedanja

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Seat> seats = new HashSet<Seat>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<TicketPrice> ticketPrices = new HashSet<TicketPrice>();

    @JsonManagedReference("flight-tickets")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Ticket> tickets = new HashSet<Ticket>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Rate> rating = new HashSet<Rate>();

    public Flight() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Destination getDepartureDestination() {
        return departureDestination;
    }

    public void setDepartureDestination(Destination from) {
        this.departureDestination = from;
    }

    public Destination getArrivalDestination() {
        return arrivalDestination;
    }

    public void setArrivalDestination(Destination to) {
        this.arrivalDestination = to;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(int transferNum) {
        this.transferNum = transferNum;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Set<TicketPrice> getTicketPrices() {
        return ticketPrices;
    }

    public void setTicketPrices(Set<TicketPrice> ticketPrices) {
        this.ticketPrices = ticketPrices;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<Rate> getRating() {
        return rating;
    }

    public void setRating(Set<Rate> rating) {
        this.rating = rating;
    }
}

package org.tim16.booker.model.airline;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.tim16.booker.model.users.Reservation;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "flight_reservations")
public class FlightReservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @OneToOne
    private Reservation reservation;

    @ManyToOne
    private Flight flight;

    @OneToOne
    private Ticket ticket;

    private int checked;

    private int carryOn;

    private Float totalPrice;

    private String firstName;
    private String lastName;
    private String passport;

    public FlightReservation() { /* empty constructor */}

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getCarryOn() {
        return carryOn;
    }

    public void setCarryOn(int carryOn) {
        this.carryOn = carryOn;
    }

    public Integer getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }


    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}

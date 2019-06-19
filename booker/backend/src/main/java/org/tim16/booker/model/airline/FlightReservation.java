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

    private Float totalPrice;

    public FlightReservation() { /* empty constructor */}

    public Integer getId() {
        return id;
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

}

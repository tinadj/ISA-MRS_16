package org.tim16.booker.model.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.tim16.booker.model.airline.FlightReservation;
import org.tim16.booker.model.hotel.HotelReservation;
import org.tim16.booker.model.rent_a_car.RentACarReservation;

import javax.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    private Integer id;

    @JsonBackReference("user-reservation")
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private RegisteredUser user;

    @OneToOne
    private FlightReservation flight;

    @OneToOne
    private HotelReservation hotel;

    @OneToOne
    private RentACarReservation rentACarReservation;

    public Reservation() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public FlightReservation getFlight() {
        return flight;
    }

    public void setFlight(FlightReservation flight) {
        this.flight = flight;
    }

    public HotelReservation getHotel() {
        return hotel;
    }

    public void setHotel(HotelReservation hotel) {
        this.hotel = hotel;
    }

    public RentACarReservation getRentACarReservation() {
        return rentACarReservation;
    }

    public void setRentACarReservation(RentACarReservation rentACarReservation) {
        this.rentACarReservation = rentACarReservation;
    }
}

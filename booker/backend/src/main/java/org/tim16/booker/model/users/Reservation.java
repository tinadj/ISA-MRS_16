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
    private FlightReservation flightReservation;

    @OneToOne
    private HotelReservation hotelReservation;

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

    public FlightReservation getFlightReservation() {
        return flightReservation;
    }

    public void setFlightReservation(FlightReservation flightReservation) {
        this.flightReservation = flightReservation;
    }

    public HotelReservation getHotelReservation() {
        return hotelReservation;
    }

    public void setHotelReservation(HotelReservation hotelReservation) {
        this.hotelReservation = hotelReservation;
    }

    public RentACarReservation getRentACarReservation() {
        return rentACarReservation;
    }

    public void setRentACarReservation(RentACarReservation rentACarReservation) {
        this.rentACarReservation = rentACarReservation;
    }
}

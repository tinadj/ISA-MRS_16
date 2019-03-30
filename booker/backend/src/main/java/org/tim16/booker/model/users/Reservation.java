package org.tim16.booker.model.users;

import org.tim16.booker.model.airline.FlightReservation;
import org.tim16.booker.model.hotel.HotelReservation;
import org.tim16.booker.model.rent_a_car.RentACarReservation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    private Integer id;
    //private User user;
    //private FlightReservation flight;
    //private HotelReservation hotel;
    //private RentACarReservation rentACarReservation;

    public Reservation() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

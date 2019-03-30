package org.tim16.booker.model.hotel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "hotel_reservations")
public class HotelReservation {

    @Id
    private Integer id;
    //private Set<Room> rooms = new HashSet<Room>();
    //private Map<String, Float> extraServices = new HashMap<String, Float>();
    //private Date checkInDate;
    private Integer nights;
    private Integer guests;
    private Float totalPrice;

    public HotelReservation() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNights() {
        return nights;
    }

    public void setNights(Integer nights) {
        this.nights = nights;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}

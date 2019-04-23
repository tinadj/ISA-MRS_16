package org.tim16.booker.model.admins;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.tim16.booker.model.hotel.Hotel;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "hotel_admins")
public class HotelAdmin extends User {

    @JsonManagedReference("hotel-admin")
    @ManyToOne
    @JoinColumn(name = "hotel", referencedColumnName = "id")
    private Hotel hotel;


    public HotelAdmin() {}

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}

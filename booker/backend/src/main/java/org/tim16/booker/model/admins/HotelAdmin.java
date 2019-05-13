package org.tim16.booker.model.admins;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.tim16.booker.model.hotel.Hotel;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class HotelAdmin extends User {

    @JsonManagedReference("hotel-admin")
    @ManyToOne
    @JoinColumn(name = "hotel", referencedColumnName = "id")
    private Hotel hotel;

    private Boolean passChanged;

    public HotelAdmin() {}


    public HotelAdmin(String username, String password, String name, String lastname, String email, String city, Integer phoneNum) {
        super(username, password, name, lastname, email, city, phoneNum);
        this.passChanged = false;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Boolean getPassChanged() {
        return passChanged;
    }

    public void setPassChanged(Boolean passChanged) {
        this.passChanged = passChanged;
    }
}

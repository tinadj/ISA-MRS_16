package org.tim16.booker.model.admins;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.tim16.booker.model.airline.Airline;
import org.tim16.booker.model.users.User;

import javax.persistence.*;

@Entity
public class AirlineAdmin extends User {

    @JsonManagedReference("airline-admin")
    @ManyToOne
    @JoinColumn(name = "airline", referencedColumnName = "id")
    private Airline airline;

    private Boolean passChanged;

    public AirlineAdmin() {}

    public AirlineAdmin(String username, String password, String name, String lastname, String email, String city, Integer phoneNum) {
        super(username, password, name, lastname, email, city, phoneNum);
        this.passChanged = false;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Boolean getPassChanged() {
        return passChanged;
    }

    public void setPassChanged(Boolean passChanged) {
        this.passChanged = passChanged;
    }
}

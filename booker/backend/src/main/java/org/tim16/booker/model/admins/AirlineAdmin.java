package org.tim16.booker.model.admins;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.tim16.booker.model.airline.Airline;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "airline_admins")
public class AirlineAdmin extends User {

    @JsonManagedReference("airline-admin")
    @ManyToOne
    @JoinColumn(name = "airline", referencedColumnName = "id")
    private Airline airline;

    public AirlineAdmin() {}

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

}

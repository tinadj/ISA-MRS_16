package org.tim16.booker.model.admins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.tim16.booker.model.airline.Airline;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
public class AirlineAdmin extends User {

    private String name;

    private String lastname;

    private String email;

    private String city;

    private Integer phoneNum;

    @JsonManagedReference("airline-admin")
    @ManyToOne
    @JoinColumn(name = "airline", referencedColumnName = "id")
    private Airline airline;

    public AirlineAdmin() {}

    public AirlineAdmin(String name, String lastname, String email, String city, Integer phoneNum, Airline airline) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.city = city;
        this.phoneNum = phoneNum;
        this.airline = airline;
    }

    public AirlineAdmin(String username, String password, String name, String lastname, String email, String city, Integer phoneNum) {
        super(username, password);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.city = city;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String address) {
        this.city = address;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }



}

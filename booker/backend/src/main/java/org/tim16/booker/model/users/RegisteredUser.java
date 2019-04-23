package org.tim16.booker.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;
import org.tim16.booker.model.utility.Rate;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;

import java.util.Set;

@Entity
@Table(name = "registered_users")
public class RegisteredUser extends User {

    private Integer bonusPts;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Friendship> friends = new HashSet<Friendship>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<Reservation>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Rate> rates = new HashSet<Rate>();

    public RegisteredUser() {}


    public RegisteredUser(String username, String password, String name, String lastname, String email, String city,
                          Integer phoneNum, Integer bonusPts, UserType type) {
        super(username, password, name, lastname, email, city, phoneNum);
        this.bonusPts = bonusPts;
        this.type = type;
    }

    public Integer getBonusPts() {
        return bonusPts;
    }

    public void setBonusPts(Integer bonusPts) {
        this.bonusPts = bonusPts;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Set<Friendship> getFriends() {
        return friends;
    }

    public void setFriends(Set<Friendship> friends) {
        this.friends = friends;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }





}

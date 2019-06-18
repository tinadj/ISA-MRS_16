package org.tim16.booker.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.tim16.booker.model.utility.Rate;

import javax.persistence.*;
import java.util.HashSet;

import java.util.Set;

@Entity
public class RegisteredUser extends User {

    private Integer bonusPts;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @JsonManagedReference("user-friendships")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Friendship> friends = new HashSet<>();

    @JsonManagedReference("user-reservations")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<>();

    @JsonManagedReference("user-rates")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Rate> rates = new HashSet<>();


    public RegisteredUser() {  /* empty constructor */}

    public RegisteredUser(String username, String password, String name, String lastname, String email, String city, Integer phoneNum) {
        super(username, password, name, lastname, email, city, phoneNum);
        this.bonusPts = 0;
        this.type = UserType.REGULAR;
        this.friends = new HashSet<>();
        this.reservations = new HashSet<>();
        this.rates = new HashSet<>();
        super.setEnabled(false);
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

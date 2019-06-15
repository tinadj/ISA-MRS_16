package org.tim16.booker.model.users;

import org.tim16.booker.model.utility.Rate;

import javax.persistence.*;
import java.util.HashSet;

import java.util.Set;

@Entity
public class RegisteredUser extends User {

    private Integer bonusPts;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Friendship> friends = new HashSet<Friendship>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<Reservation>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Rate> rates = new HashSet<Rate>();

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

    public void addReservations(Reservation r)
    {
        if (r.getUser() != null)
        {
            r.getUser().getReservations().remove(r);
        }
        r.setUser(this);
        this.getReservations().add(r);
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

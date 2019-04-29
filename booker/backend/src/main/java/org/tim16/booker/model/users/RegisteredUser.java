package org.tim16.booker.model.users;

import org.tim16.booker.model.utility.Rate;
import org.tim16.booker.model.utility.User;

import javax.persistence.*;
import java.util.HashSet;

import java.util.Set;

@Entity
public class RegisteredUser extends User {

    private String name;

    private String lastname;

    private String email;

    private String city;

    private Integer phoneNum;

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

    public RegisteredUser(String username, String password, String name, String lastname, String email, String city, Integer phoneNum) {
        super(username, password);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.city = city;
        this.phoneNum = phoneNum;
        this.bonusPts = 0;
        this.type = UserType.REGULAR;
        this.friends = new HashSet<>();
        this.reservations = new HashSet<>();
        this.rates = new HashSet<>();
        super.setEnabled(false);
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

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
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

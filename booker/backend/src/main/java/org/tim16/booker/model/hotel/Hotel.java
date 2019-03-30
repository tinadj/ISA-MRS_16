package org.tim16.booker.model.hotel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.tim16.booker.model.admins.HotelAdmin;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.model.utility.Rate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    String name;

    @ManyToOne
    @JoinColumn(name = "address", referencedColumnName = "id", nullable = false)
    private Destination address;

    private String description;

    @JsonManagedReference("hotel-room")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="hotel")
    private Set<Room> rooms = new HashSet<Room>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Rate> rating = new HashSet<Rate>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<HotelReservation> reservations = new HashSet<HotelReservation>();

    @JsonBackReference("hotel-admin")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="hotel")
    private Set<HotelAdmin> admins = new HashSet<HotelAdmin>();

    public Hotel() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Destination getAddress() {
        return address;
    }

    public void setAddress(Destination address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<Rate> getRating() {
        return rating;
    }

    public void setRating(Set<Rate> rating) {
        this.rating = rating;
    }

    public Set<HotelReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<HotelReservation> reservations) {
        this.reservations = reservations;
    }

    public Set<HotelAdmin> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<HotelAdmin> admins) {
        this.admins = admins;
    }
}

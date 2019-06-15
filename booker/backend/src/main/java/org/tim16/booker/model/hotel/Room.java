package org.tim16.booker.model.hotel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.tim16.booker.model.utility.Rate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonBackReference("hotel-rooms")
    @ManyToOne()
    @JoinColumn(name = "hotel", referencedColumnName = "id", nullable = false)
    private Hotel hotel;

    private Integer floor;
    private Integer roomNum;
    private Integer beds;
    private Boolean balcony;

    private Integer discount;

    @JsonBackReference("room-price")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="room")
    private Set<RoomPrice> roomPrice = new HashSet<RoomPrice>();

    @ElementCollection(targetClass = ExtraService.class)
    @Enumerated(EnumType.STRING)
    private Set<ExtraService> extraservices = new HashSet<ExtraService>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Rate> rating = new HashSet<Rate>();

    public Room() { /* empty constructor */}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Boolean getBalcony() {
        return balcony;
    }

    public void setBalcony(Boolean balcony) {
        this.balcony = balcony;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Set<RoomPrice> getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Set<RoomPrice> roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Set<ExtraService> getExtraServices() {
        return extraservices;
    }

    public void setExtraServices(Set<ExtraService> extraservices) {
        this.extraservices = extraservices;
    }

    public Set<Rate> getRating() {
        return rating;
    }

    public void setRating(Set<Rate> rating) {
        this.rating = rating;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }
}

package org.tim16.booker.model.hotel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.tim16.booker.model.admins.HotelAdmin;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.model.utility.Rate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hotels")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Destination address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<ExtraServicePrice> extraServicePrices = new HashSet<ExtraServicePrice>();

    @Column(name = "description")
    private String description;

    private Integer floors;

    private  Integer maxRoomsNum;

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

    public Hotel() { /* empty constructor */}

    /* Zadovoljava obostranu vezu izmedju sobe i hotela (ovde se vezuje soba za hotel) */
    public void add(Room p) {
        if (p.getHotel() != null)
        {
            p.getHotel().getRooms().remove(p);
        }

        p.setHotel(this);
        this.getRooms().add(p);
    }

    public void removeRoom(Integer id) {
        for (Room r : getRooms()) {
            if (r.getId() == id) {
                this.getRooms().remove(r);
                return;
            }
        }
    }

    public void addAdmin(HotelAdmin admin) {
        if (admin.getHotel() != null)
            admin.getHotel().getAdmins().remove(admin);

        admin.setHotel(this);
        this.getAdmins().add(admin);
    }

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

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getMaxRoomsNum() {
        return maxRoomsNum;
    }

    public void setMaxRoomsNum(Integer maxRoomsNum) {
        this.maxRoomsNum = maxRoomsNum;
    }

    public Set<ExtraServicePrice> getExtraServicePrices() {
        return extraServicePrices;
    }

    public void setExtraServicePrices(Set<ExtraServicePrice> extraServicePrices) {
        this.extraServicePrices = extraServicePrices;
    }
}

package org.tim16.booker.model.airline;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.tim16.booker.model.admins.AirlineAdmin;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.model.utility.Rate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "airlines")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Destination address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String description;

    @ManyToMany
    @JoinTable(name = "airline_destinations",
            joinColumns = @JoinColumn(name = "airline_id"),
            inverseJoinColumns = @JoinColumn(name = "destination_id"))
    private Set<Destination> destinations = new HashSet<Destination>();

    @JsonBackReference("airline-flight")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Flight> flights = new HashSet<Flight>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Ticket> discountTickets = new HashSet<Ticket>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<LuggagePrice> luggagePrices = new HashSet<LuggagePrice>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Rate> rating = new HashSet<Rate>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<FlightReservation> reservations = new HashSet<FlightReservation>();

    @JsonBackReference("airline-admin")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="airline")
    private Set<AirlineAdmin> admins = new HashSet<AirlineAdmin>();

    public Airline() {}

    public void addAdmin(AirlineAdmin admin) {
        if (admin.getAirline() != null)
            admin.getAirline().getAdmins().remove(admin);

        admin.setAirline(this);
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

    public Set<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(Set<Destination> destinations) {
        this.destinations = destinations;
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }

    public Set<Ticket> getDiscountTickets() {
        return discountTickets;
    }

    public void setDiscountTickets(Set<Ticket> discountTickets) {
        this.discountTickets = discountTickets;
    }

    public Set<LuggagePrice> getLuggagePrices() {
        return luggagePrices;
    }

    public void setLuggagePrices(Set<LuggagePrice> luggagePrices) {
        this.luggagePrices = luggagePrices;
    }

    public Set<Rate> getRating() {
        return rating;
    }

    public void setRating(Set<Rate> rating) {
        this.rating = rating;
    }

    public Set<FlightReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<FlightReservation> reservations) {
        this.reservations = reservations;
    }

    public Set<AirlineAdmin> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<AirlineAdmin> admins) {
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
}

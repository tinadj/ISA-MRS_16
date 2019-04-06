package org.tim16.booker.model.rent_a_car;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.tim16.booker.model.admins.RentACarAdmin;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.model.utility.Rate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "rent_a_cars")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RentACar {

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

    @Column(name = "descripiton")
    private String description;

    @JsonBackReference("rent_a_car-branch_office")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="rentACar")
    private Set<BranchOffice> branchOffices = new HashSet<BranchOffice>();

    @JsonManagedReference("rent_a_car-vehicles")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Vehicle> vehicles = new HashSet<Vehicle>();


    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<VehiclePrice> vehiclePriceList = new HashSet<VehiclePrice>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Rate> rating = new HashSet<Rate>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<RentACarReservation> reservations = new HashSet<RentACarReservation>();

    @JsonBackReference("rent_a_car-admin")
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy = "rentACar")
    private Set<RentACarAdmin> admins = new HashSet<RentACarAdmin>();


    public RentACar() {}

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

    public Set<BranchOffice> getBranchOffices() {
        return branchOffices;
    }

    public void setBranchOffices(Set<BranchOffice> branchOffices) {
        this.branchOffices = branchOffices;
    }

    public Set<VehiclePrice> getVehiclePriceList() {
        return vehiclePriceList;
    }

    public void setVehiclePriceList(Set<VehiclePrice> vehiclePriceList) {
        this.vehiclePriceList = vehiclePriceList;
    }

    public Set<Rate> getRating() {
        return rating;
    }

    public void setRating(Set<Rate> rating) {
        this.rating = rating;
    }

    public Set<RentACarReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<RentACarReservation> reservations) {
        this.reservations = reservations;
    }

    public Set<RentACarAdmin> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<RentACarAdmin> admins) {
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


    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}

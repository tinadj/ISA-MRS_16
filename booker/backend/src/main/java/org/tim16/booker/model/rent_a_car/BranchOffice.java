package org.tim16.booker.model.rent_a_car;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.tim16.booker.model.utility.Destination;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "branch_offices")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BranchOffice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonBackReference("rent_a_car-branch_office")
    @ManyToOne
    @JoinColumn(name = "rentACar", referencedColumnName = "id", nullable = false)
    private RentACar rentACar;

    @Column( nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Destination address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    public BranchOffice() { /* empty constructor */}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RentACar getRentACar() {
        return rentACar;
    }

    public void setRentACar(RentACar rentACar) {
        this.rentACar = rentACar;
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

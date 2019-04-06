package org.tim16.booker.model.rent_a_car;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.tim16.booker.model.utility.Destination;

import javax.persistence.*;

@Entity
@Table(name = "branch_offices")
public class BranchOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonManagedReference("rent_a_car-branch_office")
    @ManyToOne
    @JoinColumn(name = "rentACar", referencedColumnName = "id", nullable = false)
    private RentACar rentACar;

    @Column( nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Destination address;

    public BranchOffice() {}

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

}

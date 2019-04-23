package org.tim16.booker.model.admins;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rent_a_car_admins")
public class RentACarAdmin extends User {

    @JsonManagedReference("rent_a_car-admin")
    @ManyToOne
    @JoinColumn(name = "rentACar", referencedColumnName = "id")
    private RentACar rentACar;

    public RentACarAdmin() {}

    public RentACar getRentACar() {
        return rentACar;
    }

    public void setRentACar(RentACar rentACar) {
        this.rentACar = rentACar;
    }

}

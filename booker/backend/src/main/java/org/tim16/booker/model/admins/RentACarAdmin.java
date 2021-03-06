package org.tim16.booker.model.admins;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.users.User;

import javax.persistence.*;

@Entity
public class RentACarAdmin extends User {

    @JsonManagedReference("rent_a_car-admin")
    @ManyToOne
    @JoinColumn(name = "rentACar", referencedColumnName = "id")
    private RentACar rentACar;

    private  Boolean passChanged;

    public RentACarAdmin() {}

    public RentACarAdmin(String username, String password, String name, String lastname, String email, String city, Integer phoneNum) {
        super(username, password, name, lastname, email, city, phoneNum);
        this.passChanged = false;
    }

    public RentACar getRentACar() {
        return rentACar;
    }

    public void setRentACar(RentACar rentACar) {
        this.rentACar = rentACar;
    }

    public Boolean getPassChanged() {
        return passChanged;
    }

    public void setPassChanged(Boolean passChanged) {
        this.passChanged = passChanged;
    }
}

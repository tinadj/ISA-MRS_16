package org.tim16.booker.model.admins;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.utility.Authority;

import javax.persistence.*;

@Entity
@Table(name = "rent_a_car_admins")
public class RentACarAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;

    private String lastname;

    private String email;

    private String address;

    private Integer phoneNum;

    @JsonManagedReference("rent_a_car-admin")
    @ManyToOne
    @JoinColumn(name = "rentACar", referencedColumnName = "id", nullable = false)
    private RentACar rentACar;

    @ManyToOne()
    @JoinColumn(name="user_authority", referencedColumnName = "id")
    private Authority authority;

    public RentACarAdmin() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    public RentACar getRentACar() {
        return rentACar;
    }

    public void setRentACar(RentACar rentACar) {
        this.rentACar = rentACar;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}

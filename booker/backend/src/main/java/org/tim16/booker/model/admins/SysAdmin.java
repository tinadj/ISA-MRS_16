package org.tim16.booker.model.admins;

import org.springframework.security.core.userdetails.UserDetails;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class SysAdmin extends User {

    private String name;

    private String lastname;

    private String email;

    private String city;

    private Integer phoneNum;

    private Boolean passChanged;

    public SysAdmin() {}

    public SysAdmin(String username, String password) {
        super(username, password);
    }

    public SysAdmin(String name, String lastname, String email, String city, Integer phoneNum) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.city = city;
        this.phoneNum = phoneNum;
    }

    public SysAdmin(String username, String password, String name, String lastname, String email, String city, Integer phoneNum) {
        super(username, password);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.city = city;
        this.phoneNum = phoneNum;
        this.passChanged = false;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Boolean getPassChanged() {
        return passChanged;
    }

    public void setPassChanged(Boolean passChanged) {
        this.passChanged = passChanged;
    }
}


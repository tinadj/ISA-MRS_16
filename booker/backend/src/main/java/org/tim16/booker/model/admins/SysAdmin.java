package org.tim16.booker.model.admins;

import org.springframework.security.core.userdetails.UserDetails;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class SysAdmin extends User {

    private Boolean passChanged;

    public SysAdmin() {}

    public SysAdmin(String username, String password) {
        super(username, password);
    }

    public SysAdmin(String username, String password, String name, String lastname, String email, String city, Integer phoneNum) {
        super(username, password, name, lastname, email, city, phoneNum);
        this.passChanged = false;
    }

    public Boolean getPassChanged() {
        return passChanged;
    }

    public void setPassChanged(Boolean passChanged) {
        this.passChanged = passChanged;
    }
}


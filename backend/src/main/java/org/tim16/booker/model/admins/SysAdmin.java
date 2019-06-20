package org.tim16.booker.model.admins;

import org.tim16.booker.model.users.User;

import javax.persistence.*;

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


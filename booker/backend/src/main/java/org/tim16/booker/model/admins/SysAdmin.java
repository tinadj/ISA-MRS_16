package org.tim16.booker.model.admins;

import org.tim16.booker.model.utility.User;

import javax.persistence.*;

@Entity
@Table(name = "sys_admin")
public class SysAdmin extends User {
    public SysAdmin(String username, String password) {
        super(username, password);
    }
}


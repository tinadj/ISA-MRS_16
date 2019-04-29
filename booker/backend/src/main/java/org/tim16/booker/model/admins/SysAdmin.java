package org.tim16.booker.model.admins;

import org.springframework.security.core.userdetails.UserDetails;
import org.tim16.booker.model.utility.Authority;
import org.tim16.booker.model.utility.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class SysAdmin extends User {

    public SysAdmin() {}

    public SysAdmin(String username, String password) {
        super(username, password);
    }
}


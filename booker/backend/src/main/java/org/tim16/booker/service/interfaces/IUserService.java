package org.tim16.booker.service.interfaces;


import org.tim16.booker.model.users.User;

import java.util.List;

public interface IUserService {
    User findByUsername(String username);
    List<User> findAll ();
}

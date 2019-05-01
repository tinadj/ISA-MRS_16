package org.tim16.booker.service.interfaces;


import org.tim16.booker.model.utility.User;

import java.util.List;

public interface IUserService {
    User findById(Integer id);
    User findByUsername(String username);
    List<User> findAll ();
}

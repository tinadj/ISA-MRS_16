package org.tim16.booker.service.interfaces;
import org.tim16.booker.model.users.User;

import java.util.List;

public interface IUserService {

    public User findOne(Integer id);
    public List<User> findAll();
    public User create(User hotel);
    public User update(User hotel);
    public void remove(Integer id);
}

package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tim16.booker.model.users.User;
import org.tim16.booker.repository.UserRepository;
import org.tim16.booker.service.interfaces.IUserService;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    public User findOne(Integer id) {
        return repository.getOne(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User create(User hotel) {
        return repository.save(hotel);
    }

    public User update(User hotel) {
        return repository.save(hotel);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }
}

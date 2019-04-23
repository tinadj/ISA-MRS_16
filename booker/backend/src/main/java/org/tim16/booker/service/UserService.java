package org.tim16.booker.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.tim16.booker.model.users.User;
import org.tim16.booker.repository.UserRepository;
import org.tim16.booker.service.interfaces.IUserService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username);
        return u;
    }

    public User findById(Integer id) {
        User u = userRepository.getOne(id);
        return u;
    }

    public List<User> findAll() {
        List<User> result = userRepository.findAll();
        return result;
    }

    public void save (User user) {
        userRepository.save(user);
    }

}

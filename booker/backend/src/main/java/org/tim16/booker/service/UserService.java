package org.tim16.booker.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.tim16.booker.model.users.User;
import org.tim16.booker.repository.UserRepository;
import org.tim16.booker.service.interfaces.IUserService;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {return  userRepository.findByEmail(email);}

}

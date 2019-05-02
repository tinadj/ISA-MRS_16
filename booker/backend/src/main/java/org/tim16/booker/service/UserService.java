package org.tim16.booker.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.tim16.booker.model.utility.User;
import org.tim16.booker.repository.UserRepository;
import org.tim16.booker.service.interfaces.IUserService;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }

    public List<User> findAll() {
        List<User> result = userRepository.findAll();
        return result;
    }

    public void save (User user) {
        userRepository.save(user);
    }



}

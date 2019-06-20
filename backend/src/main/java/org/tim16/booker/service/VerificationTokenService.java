package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.tim16.booker.model.utility.VerificationToken;
import org.tim16.booker.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository repository;

    public VerificationToken create(VerificationToken token) {
        return repository.save(token);
    }
}

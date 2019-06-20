package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tim16.booker.model.utility.Rate;
import org.tim16.booker.repository.RateRepository;

import java.util.List;

@Service
public class RateService {

    @Autowired
    private RateRepository repository;

    public Rate findOne(Integer id) {
        return repository.getOne(id);
    }

    public List<Rate> findAll() {
        return repository.findAll();
    }

    public Rate create(Rate rate) {
        return repository.save(rate);
    }

    public Rate update(Rate rate) {
        return repository.save(rate);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }
}

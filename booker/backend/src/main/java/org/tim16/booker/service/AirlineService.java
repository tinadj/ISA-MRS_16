package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tim16.booker.model.airline.Airline;
import org.tim16.booker.repository.AirlineRepository;
import org.tim16.booker.service.interfaces.IAirlineService;

import java.util.List;

@Service
public class AirlineService implements IAirlineService {

    @Autowired
    private AirlineRepository repository;

    public Airline findOne(Integer id) { return repository.getOne(id); }

    public List<Airline> findAll() { return repository.findAll(); }

    public Airline create(Airline airline) {
        return repository.save(airline);
    }

    public Airline update(Airline airline) {
        return repository.save(airline);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }

    public Airline findByName(String name) {return repository.findByName(name);}
}
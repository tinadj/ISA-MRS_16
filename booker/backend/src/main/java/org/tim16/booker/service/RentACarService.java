package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.repository.RentACarRepository;
import org.tim16.booker.service.interfaces.IRentACarService;

import java.util.List;

@Service
public class RentACarService implements IRentACarService {

    @Autowired
    private RentACarRepository repository;

    public RentACar findOne(Integer id) { return repository.getOne(id); }

    public List<RentACar> findAll() { return repository.findAll(); }

    public RentACar create(RentACar rentACar) {
        return repository.save(rentACar);
    }

    public RentACar update(RentACar rentACar) {
        return repository.save(rentACar);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }
}

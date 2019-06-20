package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tim16.booker.model.airline.Seat;
import org.tim16.booker.repository.SeatRepository;
import org.tim16.booker.service.interfaces.ISeatService;

import java.util.List;

@Service

public class SeatService implements ISeatService {

    @Autowired
    private SeatRepository repository;

    public Seat findOne(Integer id) {
        return repository.getOne(id);
    }
    public List<Seat> findAll() {
        return repository.findAll();
    }
    public Seat create(Seat hotel) {
        return repository.save(hotel);
    }
    public Seat update(Seat hotel) {
        return repository.save(hotel);
    }
    public void remove(Integer id) {
        repository.deleteById(id);
    }
}

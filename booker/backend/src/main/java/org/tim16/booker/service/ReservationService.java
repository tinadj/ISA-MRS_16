package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tim16.booker.model.users.Reservation;
import org.tim16.booker.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository repository;

    public Reservation findOne(Integer id) { return repository.getOne(id); }

    public List<Reservation> findAll() { return repository.findAll(); }

    public Reservation create(Reservation reservation) {
        return repository.save(reservation);
    }

    public Reservation update(Reservation reservation) {
        return repository.save(reservation);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }
}

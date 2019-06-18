package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tim16.booker.model.rent_a_car.RentACarReservation;
import org.tim16.booker.repository.RacReservationRepository;

import java.util.List;

@Service
public class RacReservationService {

    @Autowired
    private RacReservationRepository repository;

    public RentACarReservation findOne(Integer id) { return repository.getOne(id); }

    public List<RentACarReservation> findAll() { return repository.findAll(); }


    public RentACarReservation create(RentACarReservation reservation) {
        return repository.save(reservation);
    }


    public RentACarReservation update(RentACarReservation reservation) {
        return repository.save(reservation);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }
}

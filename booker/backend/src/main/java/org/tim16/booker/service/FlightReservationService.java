package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tim16.booker.model.airline.FlightReservation;
import org.tim16.booker.repository.FlightReservationRepository;

import java.util.List;

@Service
public class FlightReservationService {

    @Autowired
    private FlightReservationRepository repository;

    public FlightReservation findOne(Integer id) { return repository.getOne(id); }

    public List<FlightReservation> findAll() { return repository.findAll(); }

    public FlightReservation create(FlightReservation reservation) {
        return repository.save(reservation);
    }

    public FlightReservation update(FlightReservation reservation) {
        return repository.save(reservation);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }
}

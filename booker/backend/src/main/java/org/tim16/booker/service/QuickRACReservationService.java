package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tim16.booker.model.rent_a_car.QuickRACReservation;
import org.tim16.booker.repository.QuickRACReservationRepository;

import java.util.List;

@Service
public class QuickRACReservationService {

    @Autowired
    private QuickRACReservationRepository repository;

    public QuickRACReservation findOne(Integer id) { return repository.getOne(id); }

    public List<QuickRACReservation> findAll() { return repository.findAll(); }

    public QuickRACReservation create(QuickRACReservation quickRACReservation) {
        return repository.save(quickRACReservation);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }

    public void update(QuickRACReservation quickRACReservation) { repository.save(quickRACReservation);}
}

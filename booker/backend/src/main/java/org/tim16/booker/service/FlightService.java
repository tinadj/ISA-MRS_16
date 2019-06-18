package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tim16.booker.model.airline.Flight;
import org.tim16.booker.repository.FlightRepository;
import org.tim16.booker.service.interfaces.IFlightService;

import java.util.List;

@Service
public class FlightService implements IFlightService {

    @Autowired
    private FlightRepository repository;

    public Flight findOne(Integer id) {
        return repository.getOne(id);
    }

    public List<Flight> findAll() {
        return repository.findAll();
    }

    public Flight create(Flight hotel) {
        return repository.save(hotel);
    }

    public Flight update(Flight hotel) {
        return repository.save(hotel);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }

}

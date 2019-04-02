package org.tim16.booker.service.interfaces;

import org.tim16.booker.model.airline.Airline;

import java.util.List;

public interface IAirlineService {

    public Airline findOne(Integer id);
    public List<Airline> findAll();
    public Airline create(Airline airline);
    public Airline update(Airline airline);
    public void remove(Integer id);
}

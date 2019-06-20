package org.tim16.booker.service.interfaces;

import org.tim16.booker.model.airline.Flight;

import java.util.List;

public interface IFlightService {

    public Flight findOne(Integer id);
    public List<Flight> findAll();
    public Flight create(Flight hotel);
    public Flight update(Flight hotel);
    public void remove(Integer id);
}

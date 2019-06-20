package org.tim16.booker.service.interfaces;

import org.tim16.booker.model.utility.Destination;

import java.util.List;

public interface IDestinationService {

    public Destination findOne(Integer id);
    public List<Destination> findAll();
    public Destination create(Destination airline);
    public Destination update(Destination airline);
    public void remove(Integer id);
    public Destination findByCityAndState(String city, String state);
}

package org.tim16.booker.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.repository.DestinationRepository;
import org.tim16.booker.service.interfaces.IDestinationService;

import java.util.List;

@Service
public class DestinationService implements IDestinationService {

    @Autowired
    private DestinationRepository repository;

    public Destination findOne(Integer id) { return repository.getOne(id); }

    public List<Destination> findAll() { return repository.findAll(); }

    public Destination create(Destination airline) {
        return repository.save(airline);
    }

    public Destination update(Destination airline) {
        return repository.save(airline);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }

    public Destination findByCityAndState(String city, String state) { return repository.findByCityAndState(city, state);}

}

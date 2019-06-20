package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tim16.booker.model.hotel.Hotel;
import org.tim16.booker.repository.HotelRepository;
import org.tim16.booker.service.interfaces.IHotelService;

import java.util.List;

@Service
public class HotelService implements IHotelService {

    @Autowired
    private HotelRepository repository;


    public Hotel findOne(Integer id) {
        return repository.getOne(id);
    }

    public List<Hotel> findAll() {
        return repository.findAll();
    }

    public Hotel create(Hotel hotel) {
        return repository.save(hotel);
    }

    public Hotel update(Hotel hotel) {
        return repository.save(hotel);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }
}

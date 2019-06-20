package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tim16.booker.model.hotel.HotelReservation;
import org.tim16.booker.repository.HotelReservationRepository;



import java.util.List;

@Service
public class HotelReservationService
{
    @Autowired
    private HotelReservationRepository repository;

    public HotelReservation findOne(Integer id) {return repository.getOne(id); }

    public List<HotelReservation> findAll() { return repository.findAll(); }

    public HotelReservation create(HotelReservation reservation) { return repository.save(reservation); }

    public HotelReservation update(HotelReservation reservation) { return repository.save(reservation); }

    public void remove(Integer id) { repository.deleteById(id);}


}

package org.tim16.booker.service.interfaces;

import org.tim16.booker.model.airline.Seat;

import java.util.List;

public interface ISeatService {
    Seat findOne(Integer id);
    List<Seat> findAll();
    Seat create(Seat room);
    Seat update(Seat room);
    void remove(Integer id);
}

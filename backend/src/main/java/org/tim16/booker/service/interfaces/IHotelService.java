package org.tim16.booker.service.interfaces;

import org.tim16.booker.model.hotel.Hotel;

import java.util.List;

public interface IHotelService {

    public Hotel findOne(Integer id);
    public List<Hotel> findAll();
    public Hotel create(Hotel hotel);
    public Hotel update(Hotel hotel);
    public void remove(Integer id);
}

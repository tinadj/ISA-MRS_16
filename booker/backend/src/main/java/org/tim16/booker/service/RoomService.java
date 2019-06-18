package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tim16.booker.model.hotel.Room;
import org.tim16.booker.repository.RoomRepository;
import org.tim16.booker.service.interfaces.IRoomService;

import java.util.List;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelService hotelService;

    @Override
    public Room findOne(Integer id) {
        return roomRepository.getOne(id);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room create(Room room) {return roomRepository.save(room);}

    @Override
    public Room update(Room room)
    {
        return roomRepository.save(room);
    }

    @Override
    public void remove(Integer id) {
        roomRepository.deleteById(id);
    }
}

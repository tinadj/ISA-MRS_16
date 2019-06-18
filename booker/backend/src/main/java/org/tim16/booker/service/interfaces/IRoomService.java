package org.tim16.booker.service.interfaces;

import org.tim16.booker.dto.RoomDTO;
import org.tim16.booker.model.hotel.Room;

import java.util.List;

public interface IRoomService{
     Room findOne(Integer id);
     List<Room> findAll();
     Room create(Room room);
     Room update(Room room);
     void remove(Integer id);
}

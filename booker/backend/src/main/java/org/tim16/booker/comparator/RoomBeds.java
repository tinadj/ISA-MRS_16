package org.tim16.booker.comparator;

import org.tim16.booker.model.hotel.Room;

import java.util.Comparator;

public class RoomBeds implements Comparator<Room> {

    @Override
    public int compare(Room r1, Room r2) {
        return r1.getBeds().compareTo(r2.getBeds());
    }
}

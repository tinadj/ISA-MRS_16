package org.tim16.booker.comparator;

import org.tim16.booker.model.hotel.Room;

import java.util.Comparator;

public class RoomPrice implements Comparator<Room> {

    @Override
    public int compare(Room r1, Room r2) {
        return r1.getPrice().compareTo(r2.getPrice());
    }
}

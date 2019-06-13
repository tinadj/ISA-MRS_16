package org.tim16.booker.comparator;

import org.tim16.booker.model.hotel.Room;

import java.util.Comparator;
import java.util.Collections;
import java.util.List;

public class RoomPrice
{
    public static void RoomPriceAscending(List<Room> rooms)
    {
        Collections.sort(rooms, new Comparator<Room>()
        {

            public int compare(Room r1, Room r2)
            {
                return (r1.getPrice().compareTo(r2.getPrice()));
            }

        });
    }

    public static void RoomPriceDescending(List<Room> rooms)
    {
        Collections.sort(rooms, new Comparator<Room>()
        {

            public int compare(Room r1, Room r2)
            {
                return (r2.getPrice().compareTo(r1.getPrice()));
            }

        });
    }
}

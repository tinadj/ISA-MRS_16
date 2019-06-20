package org.tim16.booker.comparator;

import org.tim16.booker.model.hotel.Hotel;

import java.util.Comparator;

public class HotelName implements Comparator<Hotel> {
    @Override
    public int compare(Hotel h1, Hotel h2)
    {
        return h1.getName().compareTo(h2.getName());
    }

}
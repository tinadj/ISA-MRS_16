package org.tim16.booker.dto;

import java.util.Date;

public class HotelReservationDTO
{
    private Integer reservation;
    private Integer room;
    private Date checkinDate;
    private Integer nights;

    public HotelReservationDTO() {}

    public HotelReservationDTO(Integer reservation, Integer room, Date checkinDate, Integer nights) {
        this.reservation = reservation;
        this.room = room;
        this.checkinDate = checkinDate;
        this.nights = nights;
    }

    public Integer getReservation() {
        return reservation;
    }

    public void setReservation(Integer reservation) {
        this.reservation = reservation;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Integer getNights() {
        return nights;
    }

    public void setNights(Integer nights) {
        this.nights = nights;
    }
}

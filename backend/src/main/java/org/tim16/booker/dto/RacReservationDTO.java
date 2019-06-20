package org.tim16.booker.dto;

import java.util.Date;

public class RacReservationDTO {
    private Integer reservation;
    private Integer vehicle;
    private Integer pickUpLocation;
    private Integer dropOffLocation;
    private Date pickUpDate;
    private Integer days;
    private Integer passangerNum;

    public RacReservationDTO() {}

    public RacReservationDTO(Integer reservation, Integer vehicle, Integer pickUpLocation, Integer dropOffLocation, Date pickUpDate, Integer days, Integer passangerNum) {
        this.reservation = reservation;
        this.vehicle = vehicle;
        this.pickUpLocation = pickUpLocation;
        this.dropOffLocation = dropOffLocation;
        this.pickUpDate = pickUpDate;
        this.days = days;
        this.passangerNum = passangerNum;
    }

    public Integer getReservation() {
        return reservation;
    }

    public void setReservation(Integer reservation) {
        this.reservation = reservation;
    }

    public Integer getVehicle() {
        return vehicle;
    }

    public void setVehicle(Integer vehicle) {
        this.vehicle = vehicle;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getPassangerNum() {
        return passangerNum;
    }

    public void setPassangerNum(Integer passangerNum) {
        this.passangerNum = passangerNum;
    }

    public Integer getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(Integer pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public Integer getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(Integer dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }


}

package org.tim16.booker.dto;

import org.tim16.booker.model.utility.Destination;

import java.time.LocalDateTime;

public class FlightDTO {
    private int airlineId;

    public int getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(int airlineId) {
        this.airlineId = airlineId;
    }

    private int departure;

    public int getDeparture() {
        return departure;
    }

    public void setDeparture(int departure) {
        this.departure = departure;
    }

    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTransferNumber() {
        return transferNumber;
    }

    public void setTransferNumber(int transferNumber) {
        this.transferNumber = transferNumber;
    }

    public int getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(int firstClass) {
        this.firstClass = firstClass;
    }

    public float getFirstClassPrice() {
        return firstClassPrice;
    }

    public void setFirstClassPrice(float firstClassPrice) {
        this.firstClassPrice = firstClassPrice;
    }

    public int getBusinessClass() {
        return businessClass;
    }

    public void setBusinessClass(int businessClass) {
        this.businessClass = businessClass;
    }

    public float getBusinessClassPrice() {
        return businessClassPrice;
    }

    public void setBusinessClassPrice(float businessClassPrice) {
        this.businessClassPrice = businessClassPrice;
    }

    public int getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(int economyClass) {
        this.economyClass = economyClass;
    }

    public float getEconomyClassPrice() {
        return economyClassPrice;
    }

    public void setEconomyClassPrice(float economyClassPrice) {
        this.economyClassPrice = economyClassPrice;
    }

    private int arrival;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int transferNumber;
    private int firstClass;
    private float firstClassPrice;
    private int businessClass;
    private float businessClassPrice;
    private int economyClass;
    private float economyClassPrice;

    public FlightDTO() {}


}

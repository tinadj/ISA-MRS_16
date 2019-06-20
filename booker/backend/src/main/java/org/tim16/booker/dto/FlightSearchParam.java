package org.tim16.booker.dto;

import java.util.Date;

public class FlightSearchParam {
    private String departure;
    private String arrival;
    private Date departureDate;
    private Date returnDate;

    public FlightSearchParam(String departure, String arrival, Date departureDate, Date returnDate) {
        this.departure = departure;
        this.arrival = arrival;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}

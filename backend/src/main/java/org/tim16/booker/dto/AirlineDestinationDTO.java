package org.tim16.booker.dto;

import org.tim16.booker.model.airline.Airline;

public class AirlineDestinationDTO {

    private Integer id;
    private String city;
    private String state;
    private Integer airlineId;

    public AirlineDestinationDTO() {}

    public AirlineDestinationDTO(Integer airline, String city, String state) {
        this.airlineId = airline;
        this.city = city;
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Integer getAirlineId() { return airlineId; }

    public void setAirline(Integer airline) { this.airlineId = airline;}

    public void setState(String state) {
        this.state = state;
    }
}

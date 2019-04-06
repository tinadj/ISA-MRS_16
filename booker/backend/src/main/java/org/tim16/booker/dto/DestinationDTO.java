package org.tim16.booker.dto;

public class DestinationDTO {
    private Integer id;
    private String city;
    private String state;

    public DestinationDTO() {}

    public DestinationDTO(String city, String state) {
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

    public void setState(String state) {
        this.state = state;
    }
}

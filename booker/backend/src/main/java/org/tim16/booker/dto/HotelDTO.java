package org.tim16.booker.dto;

import java.math.BigDecimal;

public class HotelDTO {
    private Integer id;
    private String name;
    private DestinationDTO address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String description;
    private Integer floors;
    private Integer maxRoomsNum;

    public HotelDTO() {}

    public HotelDTO(Integer id, String name, DestinationDTO address, BigDecimal latitude, BigDecimal longitude, String description, Integer floors, Integer maxRoomsNum) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.floors = floors;
        this.maxRoomsNum = maxRoomsNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DestinationDTO getAddress() {
        return address;
    }

    public void setAddress(DestinationDTO address) {
        this.address = address;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getMaxRoomsNum() {
        return maxRoomsNum;
    }

    public void setMaxRoomsNum(Integer maxRoomsNum) {
        this.maxRoomsNum = maxRoomsNum;
    }
}

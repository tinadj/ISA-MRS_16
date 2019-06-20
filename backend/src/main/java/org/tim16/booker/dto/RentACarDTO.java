package org.tim16.booker.dto;

import java.math.BigDecimal;

public class RentACarDTO {
    private Integer id;
    private String name;
    private String description;
    private DestinationDTO address;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public RentACarDTO() {}

    public RentACarDTO(Integer id, String name, String description, DestinationDTO address, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
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
}

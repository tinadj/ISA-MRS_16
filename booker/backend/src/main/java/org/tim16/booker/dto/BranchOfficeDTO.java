package org.tim16.booker.dto;

import java.math.BigDecimal;

public class BranchOfficeDTO {

    private Integer id;
    private RentACarDTO rentACar;
    private String name;
    private DestinationDTO address;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public BranchOfficeDTO() { }

    public BranchOfficeDTO(Integer id, RentACarDTO rentACar, String name, DestinationDTO address, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.rentACar = rentACar;
        this.name = name;
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

    public RentACarDTO getRentACar() {
        return rentACar;
    }

    public void setRentACar(RentACarDTO rentACar) {
        this.rentACar = rentACar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

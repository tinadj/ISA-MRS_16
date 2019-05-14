package org.tim16.booker.dto;

import java.util.Date;

public class VehicleSearchParamsDTO {
    private Integer racID;
    private String pickUpLocation;
    private Date pickUpDate;
    private Date dropOffDate;
    private String dropOffLocation;
    private Integer vehicleType;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer criteria;

    public VehicleSearchParamsDTO() {}

    public VehicleSearchParamsDTO(Integer racID, String pickUpLocation, Date pickUpDate, Date dropOffDate,
                                  String dropOffLocation, Integer vehicleType, Integer minPrice, Integer maxPrice, Integer criteria) {
        this.racID = racID;
        this.pickUpLocation = pickUpLocation;
        this.pickUpDate = pickUpDate;
        this.dropOffDate = dropOffDate;
        this.dropOffLocation = dropOffLocation;
        this.vehicleType = vehicleType;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.criteria = criteria;
    }

    public Integer getRacID() {
        return racID;
    }

    public void setRacID(Integer racID) {
        this.racID = racID;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Date getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(Date dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getCriteria() {
        return criteria;
    }

    public void setCriteria(Integer criteria) {
        this.criteria = criteria;
    }
}

package org.tim16.booker.dto;

import java.util.Date;

public class QuickRAC_DTO {
    private Integer vehicleID;
    private Date pickUpDate;
    private Date dropOffDate;
    private Integer discount;

    public QuickRAC_DTO() {}

    public QuickRAC_DTO(Integer vehicleID, Date pickUpDate, Date dropOffDate, Integer discount) {
        this.vehicleID = vehicleID;
        this.pickUpDate = pickUpDate;
        this.dropOffDate = dropOffDate;
        this.discount = discount;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}

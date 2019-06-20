package org.tim16.booker.dto;

import java.util.Date;

public class RACSearchParamsDTO {
    private String name;
    private String city;
    private String state;
    private Date pickUpDate;
    private Date returnDate;
    private Integer criteria;

    public RACSearchParamsDTO() {}

    public RACSearchParamsDTO(String name, String city, String state, Date pickUpDate, Date returnDate, Integer criteria) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
        this.criteria = criteria;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getCriteria() {
        return criteria;
    }

    public void setCriteria(Integer criteria) {
        this.criteria = criteria;
    }

}

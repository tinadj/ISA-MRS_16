package org.tim16.booker.dto;

import java.util.Date;

public class HotelsSearchParamsDTO
{
    private String name;
    private String city;
    private String state;
    private Date checkin;
    private Date checkout;
    private Integer criteria;


    public HotelsSearchParamsDTO()
    {}


    public HotelsSearchParamsDTO(String name, String city, String state, Date checkin, Date checkout, Integer criteria) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.checkin = checkin;
        this.checkout = checkout;
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

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public Integer getCriteria() {
        return criteria;
    }

    public void setCriteria(Integer criteria) {
        this.criteria = criteria;
    }
}

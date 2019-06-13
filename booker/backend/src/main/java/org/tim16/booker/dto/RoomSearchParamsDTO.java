package org.tim16.booker.dto;

import java.util.Date;

public class RoomSearchParamsDTO
{
    private Integer hotelID;
    private Date checkinDate;
    private Date checkoutDate;
    private Integer beds;
    private boolean balcony;
    private boolean breakfast;
    private boolean hotel_restaurant;
    private boolean airport_transfer;
    private boolean parking;
    private boolean pool;
    private boolean wellness_spa;
    private boolean wifi;
    private boolean tv;
    private boolean minibar;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer criteria;

    public RoomSearchParamsDTO() {}

    public RoomSearchParamsDTO(Integer hotelID, Date checkinDate, Date checkoutDate, Integer beds, boolean balcony, boolean breakfast, boolean hotel_restaurant, boolean airport_transfer, boolean parking, boolean pool, boolean wellness_spa, boolean wifi, boolean tv, boolean minibar, Integer minPrice, Integer maxPrice, Integer criteria) {
        this.hotelID = hotelID;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.beds = beds;
        this.balcony = balcony;
        this.breakfast = breakfast;
        this.hotel_restaurant = hotel_restaurant;
        this.airport_transfer = airport_transfer;
        this.parking = parking;
        this.pool = pool;
        this.wellness_spa = wellness_spa;
        this.wifi = wifi;
        this.tv = tv;
        this.minibar = minibar;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.criteria = criteria;
    }

    public Integer getHotelID() {
        return hotelID;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public Integer getBeds() {
        return beds;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public boolean isBreakfast() {
        return breakfast;
    }

    public boolean isHotel_restaurant() {
        return hotel_restaurant;
    }

    public boolean isAirport_transfer() {
        return airport_transfer;
    }

    public boolean isParking() {
        return parking;
    }

    public boolean isPool() {
        return pool;
    }

    public boolean isWellness_spa() {
        return wellness_spa;
    }

    public boolean isWifi() {
        return wifi;
    }

    public boolean isTv() {
        return tv;
    }

    public boolean isMinibar() {
        return minibar;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public Integer getCriteria() {
        return criteria;
    }

    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public void setBreakfast(boolean breakfast) {
        this.breakfast = breakfast;
    }

    public void setHotel_restaurant(boolean hotel_restaurant) {
        this.hotel_restaurant = hotel_restaurant;
    }

    public void setAirport_transfer(boolean airport_transfer) {
        this.airport_transfer = airport_transfer;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public void setPool(boolean pool) {
        this.pool = pool;
    }

    public void setWellness_spa(boolean wellness_spa) {
        this.wellness_spa = wellness_spa;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public void setMinibar(boolean minibar) {
        this.minibar = minibar;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setCriteria(Integer criteria) {
        this.criteria = criteria;
    }
}

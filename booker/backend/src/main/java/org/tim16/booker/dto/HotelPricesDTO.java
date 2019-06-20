package org.tim16.booker.dto;

public class HotelPricesDTO
{
    private Integer id;
    private float breakfast;
    private float hotel_restaurant;
    private float airport_transfer;
    private float parking;
    private float pool;
    private float wellness_spa;
    private float wifi;
    private float tv;
    private float minibar;

    public HotelPricesDTO()
    {}


    public HotelPricesDTO(Integer id, float breakfast, float hotel_restaurant, float airport_transfer, float parking, float pool, float wellness_spa, float wifi, float tv, float minibar) {
        this.id = id;
        this.breakfast = breakfast;
        this.hotel_restaurant = hotel_restaurant;
        this.airport_transfer = airport_transfer;
        this.parking = parking;
        this.pool = pool;
        this.wellness_spa = wellness_spa;
        this.wifi = wifi;
        this.tv = tv;
        this.minibar = minibar;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(float breakfast) {
        this.breakfast = breakfast;
    }

    public float getHotel_restaurant() {
        return hotel_restaurant;
    }

    public void setHotel_restaurant(float hotel_restaurant) {
        this.hotel_restaurant = hotel_restaurant;
    }

    public float getAirport_transfer() {
        return airport_transfer;
    }

    public void setAirport_transfer(float airport_transfer) {
        this.airport_transfer = airport_transfer;
    }

    public float getParking() {
        return parking;
    }

    public void setParking(float parking) {
        this.parking = parking;
    }

    public float getPool() {
        return pool;
    }

    public void setPool(float pool) {
        this.pool = pool;
    }

    public float getWellness_spa() {
        return wellness_spa;
    }

    public void setWellness_spa(float wellness_spa) {
        this.wellness_spa = wellness_spa;
    }

    public float getWifi() {
        return wifi;
    }

    public void setWifi(float wifi) {
        this.wifi = wifi;
    }

    public float getTv() {
        return tv;
    }

    public void setTv(float tv) {
        this.tv = tv;
    }

    public float getMinibar() {
        return minibar;
    }

    public void setMinibar(float minibar) {
        this.minibar = minibar;
    }
}

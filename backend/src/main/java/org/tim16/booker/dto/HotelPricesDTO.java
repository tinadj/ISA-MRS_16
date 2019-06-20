package org.tim16.booker.dto;

public class HotelPricesDTO
{
    private Integer id;
    private float breakfast;
    private float hotelRestaurant;
    private float airportTransfer;
    private float parking;
    private float pool;
    private float wellnessSpa;
    private float wifi;
    private float tv;
    private float minibar;

    public HotelPricesDTO()
    {}


    public HotelPricesDTO(Integer id, float breakfast, float hotelRestaurant, float airportTransfer, float parking, float pool, float wellnessSpa, float wifi, float tv, float minibar) {
        this.id = id;
        this.breakfast = breakfast;
        this.hotelRestaurant = hotelRestaurant;
        this.airportTransfer = airportTransfer;
        this.parking = parking;
        this.pool = pool;
        this.wellnessSpa = wellnessSpa;
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

    public float getHotelRestaurant() {
        return hotelRestaurant;
    }

    public void setHotelRestaurant(float hotelRestaurant) {
        this.hotelRestaurant = hotelRestaurant;
    }

    public float getAirportTransfer() {
        return airportTransfer;
    }

    public void setAirportTransfer(float airportTransfer) {
        this.airportTransfer = airportTransfer;
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

    public float getWellnessSpa() {
        return wellnessSpa;
    }

    public void setWellnessSpa(float wellnessSpa) {
        this.wellnessSpa = wellnessSpa;
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

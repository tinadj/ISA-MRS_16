package org.tim16.booker.dto;

public class RoomDTO {

    private Integer id;
    private Integer floor;
    private Integer roomNum;
    private Integer beds;
    private Boolean balcony;
    private Boolean breakfast;
    private Boolean hotelRestaurant;
    private Boolean airportTransfer;
    private Boolean parking;
    private Boolean pool;
    private Boolean wellnessSpa;
    private Boolean wifi;
    private Boolean tv;
    private Boolean minibar;
    private Float price;
    private Integer discount;
    private Integer hotelId;

    public RoomDTO() {
    }

    public RoomDTO(Integer id, Integer floor, Integer roomNum, Integer beds, Boolean balcony, Boolean breakfast, Boolean hotelRestaurant, Boolean airportTransfer, Boolean parking, Boolean pool, Boolean wellnessSpa, Boolean wifi, Boolean tv, Boolean minibar, Integer discount, Integer hotelId) {
        this.id = id;
        this.floor = floor;
        this.roomNum = roomNum;
        this.beds = beds;
        this.balcony = balcony;
        this.breakfast = breakfast;
        this.hotelRestaurant = hotelRestaurant;
        this.airportTransfer = airportTransfer;
        this.parking = parking;
        this.pool = pool;
        this.wellnessSpa = wellnessSpa;
        this.wifi = wifi;
        this.tv = tv;
        this.minibar = minibar;
        this.price = price;
        this.discount = discount;
        this.hotelId = hotelId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Boolean getBalcony() {
        return balcony;
    }

    public void setBalcony(Boolean balcony) {
        this.balcony = balcony;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Boolean getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Boolean breakfast) {
        this.breakfast = breakfast;
    }

    public Boolean getHotelRestaurant() {
        return hotelRestaurant;
    }

    public void setHotelRestaurant(Boolean hotelRestaurant) {
        this.hotelRestaurant = hotelRestaurant;
    }

    public Boolean getAirportTransfer() {
        return airportTransfer;
    }

    public void setAirportTransfer(Boolean airportTransfer) {
        this.airportTransfer = airportTransfer;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean getPool() {
        return pool;
    }

    public void setPool(Boolean pool) {
        this.pool = pool;
    }

    public Boolean getWellnessSpa() {
        return wellnessSpa;
    }

    public void setWellnessSpa(Boolean wellnessSpa) {
        this.wellnessSpa = wellnessSpa;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getTv() {
        return tv;
    }

    public void setTv(Boolean tv) {
        this.tv = tv;
    }

    public Boolean getMinibar() {
        return minibar;
    }

    public void setMinibar(Boolean minibar) {
        this.minibar = minibar;
    }
}

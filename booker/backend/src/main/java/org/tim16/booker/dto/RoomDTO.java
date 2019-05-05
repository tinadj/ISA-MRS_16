package org.tim16.booker.dto;

public class RoomDTO {

    private Integer id;
    private Integer floor;
    private Integer roomNum;
    private Integer beds;
    private Boolean balcony;
    private Integer discount;
    private Integer hotelId;

    public RoomDTO() {
    }

    public RoomDTO(Integer id, Integer floor, Integer roomNum, Integer beds, Boolean balcony, Integer discount, Integer hotelId) {
        this.id = id;
        this.floor = floor;
        this.roomNum = roomNum;
        this.beds = beds;
        this.balcony = balcony;
        this.discount = discount;
        this.hotelId = hotelId;
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
}

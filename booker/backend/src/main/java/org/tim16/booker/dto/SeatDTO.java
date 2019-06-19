package org.tim16.booker.dto;

public class SeatDTO {
    private int id;
    private int seatRow;
    private String seatLetter;
    private  String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SeatDTO(int id, int seatRow, String seatLetter, String type) {
        this.id = id;
        this.seatRow = seatRow;
        this.seatLetter = seatLetter;
        this.type = type;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public String getSeatLetter() {
        return seatLetter;
    }

    public void setSeatLetter(String seatLetter) {
        this.seatLetter = seatLetter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

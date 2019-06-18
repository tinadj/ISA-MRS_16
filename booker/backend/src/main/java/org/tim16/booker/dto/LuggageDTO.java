package org.tim16.booker.dto;

public class LuggageDTO {
    private Integer airlineId;
    private float carryOnPrice;
    private float checkedPrice;

    private LuggageDTO() {}

    public LuggageDTO(Integer airlineId, float carryOnPrice, float checkedPrice) {
        this.airlineId = airlineId;
        this.carryOnPrice = carryOnPrice;
        this.checkedPrice = checkedPrice;
    }

    public Integer getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Integer airlineId) {
        this.airlineId = airlineId;
    }

    public float getCarryOnPrice() {
        return carryOnPrice;
    }

    public void setCarryOnPrice(float carryOnPrice) {
        this.carryOnPrice = carryOnPrice;
    }

    public float getCheckedPrice() {
        return checkedPrice;
    }

    public void setCheckedPrice(float checkedPrice) {
        this.checkedPrice = checkedPrice;
    }
}

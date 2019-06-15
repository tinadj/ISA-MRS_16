package org.tim16.booker.dto;

public class VehicleDTO {

    private Integer id;
    private String name;
    private String brand;
    private String model;
    private Integer productionYear;
    private Integer seatsNum;
    private int type;
    private String description;
    private Float price;
    private RentACarDTO rentACar;
    private Integer currentlyIn;

    public VehicleDTO() {}

    public VehicleDTO(Integer id, String name, String brand, String model, Integer productionYear, Integer seatsNum, int type, String description, Float price, RentACarDTO rentACar, Integer currentlyIn) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.seatsNum = seatsNum;
        this.type = type;
        this.description = description;
        this.price = price;
        this.rentACar = rentACar;
        this.currentlyIn = currentlyIn;
    }

    public Integer getCurrentlyIn() {
        return currentlyIn;
    }

    public void setCurrentlyIn(Integer currentlyIn) {
        this.currentlyIn = currentlyIn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Integer getSeatsNum() {
        return seatsNum;
    }

    public void setSeatsNum(Integer seatsNum) {
        this.seatsNum = seatsNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RentACarDTO getRentACar() {
        return rentACar;
    }

    public void setRentACar(RentACarDTO rentACar) {
        this.rentACar = rentACar;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

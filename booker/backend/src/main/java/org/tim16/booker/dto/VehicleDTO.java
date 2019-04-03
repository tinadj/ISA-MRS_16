package org.tim16.booker.dto;

import org.tim16.booker.model.rent_a_car.VehicleType;

public class VehicleDTO {

    private Integer id;
    private String name;
    private String brand;
    private String model;
    private Integer productionYear;
    private Integer seatsNum;
    //private VehicleType type;
    private String description;
    private RentACarDTO rentACar;

    public VehicleDTO() {}

    public VehicleDTO(Integer id, String name, String brand, String model, Integer productionYear, Integer seatsNum, String description, RentACarDTO rentACar) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.seatsNum = seatsNum;
        this.description = description;
        this.rentACar = rentACar;
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
}

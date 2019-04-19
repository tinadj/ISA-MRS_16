package org.tim16.booker.dto;

public class BranchOfficeDTO {

    private Integer id;
    private RentACarDTO rentACar;
    private String name;
    private DestinationDTO address;

    public BranchOfficeDTO() { }

    public BranchOfficeDTO(Integer id, RentACarDTO rentACar, String name, DestinationDTO address) {
        this.id = id;
        this.rentACar = rentACar;
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RentACarDTO getRentACar() {
        return rentACar;
    }

    public void setRentACar(RentACarDTO rentACar) {
        this.rentACar = rentACar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DestinationDTO getAddress() {
        return address;
    }

    public void setAddress(DestinationDTO address) {
        this.address = address;
    }
}

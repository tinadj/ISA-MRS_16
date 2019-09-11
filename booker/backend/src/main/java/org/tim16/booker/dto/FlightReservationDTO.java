package org.tim16.booker.dto;

public class FlightReservationDTO {

    private Integer id;
    private Integer flightId;
    private int checked;
    private int carryOn;

    private String firstName;
    private String lastName;
    private String passport;

    private int ticketId;

    public FlightReservationDTO() {}

    public FlightReservationDTO(Integer id, Integer flightId, int checked, int carryOn, String firstName, String lastName, String passport, int ticketId) {
        this.id = id;
        this.flightId = flightId;
        this.checked = checked;
        this.carryOn = carryOn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passport = passport;
        this.ticketId = ticketId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getCarryOn() {
        return carryOn;
    }

    public void setCarryOn(int carryOn) {
        this.carryOn = carryOn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
}

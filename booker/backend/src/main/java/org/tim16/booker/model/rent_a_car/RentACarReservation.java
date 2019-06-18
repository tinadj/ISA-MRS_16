package org.tim16.booker.model.rent_a_car;

import org.tim16.booker.model.users.Reservation;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rent_a_car_reservations")
public class RentACarReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Reservation reservation;


    @ManyToOne
    private Vehicle vehicle;

    @Temporal(TemporalType.DATE)
    private Date pickUpDate;

    private Integer days;

    @ManyToOne
    private BranchOffice pickUpLocation;

    @ManyToOne
    private BranchOffice dropOffLocation;

    private Integer passangerNum;

    private Float totalPrice;

    public RentACarReservation() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getPassangerNum() {
        return passangerNum;
    }

    public void setPassangerNum(Integer passangerNum) {
        this.passangerNum = passangerNum;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public BranchOffice getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(BranchOffice pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public BranchOffice getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(BranchOffice dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

}

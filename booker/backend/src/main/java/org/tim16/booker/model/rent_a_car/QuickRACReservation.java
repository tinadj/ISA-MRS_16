package org.tim16.booker.model.rent_a_car;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "quick_rent_a_car_reservations")
public class QuickRACReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Vehicle vehicle;

    @Temporal(TemporalType.DATE)
    private Date pickUpDate;

    @Temporal(TemporalType.DATE)
    private Date dropOffDate;

    private Integer discount;

    public QuickRACReservation() {}

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

    public Date getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(Date dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}

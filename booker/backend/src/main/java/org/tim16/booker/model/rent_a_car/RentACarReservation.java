package org.tim16.booker.model.rent_a_car;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "rent_a_car_reservations")
public class RentACarReservation {

    @Id
    private Integer id;
    //private Vehicle vehicle;
    //private Date pickUpDate;
    private Integer days;
    private Integer passangerNum;
    private Float totalPrice;

    public RentACarReservation() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


}

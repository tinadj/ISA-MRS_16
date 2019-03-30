package org.tim16.booker.model.airline;

import org.tim16.booker.model.users.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flight_reservations")
public class FlightReservation {

    @Id
    private Integer id;
    //private Flight flight;
    //private User user;
    //private Set<Invitation> invitations = new HashSet<Invitation>();
    //private Set<Passanger> passangers = new HashSet<Passanger>();
    private Float totalPrice;

    public FlightReservation() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}

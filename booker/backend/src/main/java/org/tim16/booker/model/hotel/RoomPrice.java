package org.tim16.booker.model.hotel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "room_prices")
public class RoomPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "room", referencedColumnName = "id", nullable = false)
    private Room room;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date toDate;

    private Float pricePerNight;

    public RoomPrice() { /* empty constructor */}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Float getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Float pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
}

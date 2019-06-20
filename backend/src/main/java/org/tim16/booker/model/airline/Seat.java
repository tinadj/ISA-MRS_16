package org.tim16.booker.model.airline;

import javax.persistence.*;

@Entity
@Table(name = "flight_seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private Integer seatRow;

    @Column(nullable = false)
    private String seatLetter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelClass travelClass;

    public Seat() { /* empty constructor */}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }

    public String getSeatLetter() {
        return seatLetter;
    }

    public void setSeatLetter(String seatLetter) {
        this.seatLetter = seatLetter;
    }

    public TravelClass getType() {
        return travelClass;
    }

    public void setType(TravelClass travelClass) {
        this.travelClass = travelClass;
    }
}

package org.tim16.booker.model.hotel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room_reservations")
public class RoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Room room;

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<ExtraServicePrice> extraServices = new HashSet<ExtraServicePrice>();

    public RoomReservation() { /* empty constructor */}

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

    public Set<ExtraServicePrice> getExtraServices() {
        return extraServices;
    }

    public void setExtraServices(Set<ExtraServicePrice> extraServices) {
        this.extraServices = extraServices;
    }
}

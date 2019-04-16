package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tim16.booker.model.hotel.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}

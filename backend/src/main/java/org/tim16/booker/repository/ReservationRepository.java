package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tim16.booker.model.users.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}

package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tim16.booker.model.rent_a_car.QuickRACReservation;

@Repository
public interface QuickRACReservationRepository extends JpaRepository<QuickRACReservation, Integer> {
}

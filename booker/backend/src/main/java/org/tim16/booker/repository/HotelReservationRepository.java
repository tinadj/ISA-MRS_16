package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tim16.booker.model.hotel.HotelReservation;

@Repository
public interface HotelReservationRepository extends JpaRepository<HotelReservation, Integer>
{

}
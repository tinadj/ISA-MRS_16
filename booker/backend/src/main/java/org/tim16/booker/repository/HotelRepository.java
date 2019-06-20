package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tim16.booker.model.hotel.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    Hotel findByName(String name);
}

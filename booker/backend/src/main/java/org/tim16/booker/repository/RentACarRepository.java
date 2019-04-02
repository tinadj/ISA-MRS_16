package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tim16.booker.model.rent_a_car.RentACar;

@Repository
public interface RentACarRepository extends JpaRepository<RentACar, Integer> {
}

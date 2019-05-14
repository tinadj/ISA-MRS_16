package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.model.utility.User;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}

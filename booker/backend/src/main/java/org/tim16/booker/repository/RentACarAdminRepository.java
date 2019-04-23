package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tim16.booker.model.admins.RentACarAdmin;
import org.tim16.booker.model.rent_a_car.RentACar;

public interface RentACarAdminRepository extends JpaRepository<RentACarAdmin, Integer> {

    RentACarAdmin findByUsername(String username);
}

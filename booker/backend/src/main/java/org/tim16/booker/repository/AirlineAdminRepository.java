package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tim16.booker.model.admins.AirlineAdmin;

public interface AirlineAdminRepository extends JpaRepository<AirlineAdmin, Integer> {

    AirlineAdmin findByUsername(String username);
}

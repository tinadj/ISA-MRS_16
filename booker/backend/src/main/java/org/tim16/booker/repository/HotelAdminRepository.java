package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tim16.booker.model.admins.HotelAdmin;

public interface HotelAdminRepository extends JpaRepository<HotelAdmin, Integer> {

    HotelAdmin findByUsername(String username);
}

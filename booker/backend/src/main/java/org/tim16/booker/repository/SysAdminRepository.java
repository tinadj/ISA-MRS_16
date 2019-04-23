package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tim16.booker.model.admins.SysAdmin;

public interface SysAdminRepository extends JpaRepository<SysAdmin, Integer> {

    SysAdmin findByUsername(String username);
}

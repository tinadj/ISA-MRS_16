package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tim16.booker.model.users.UserType;
import org.tim16.booker.model.utility.UserDiscounts;

@Repository
public interface UserDiscountsRepository extends JpaRepository<UserDiscounts, Integer> {

    UserDiscounts findByUserType(UserType userType);
}

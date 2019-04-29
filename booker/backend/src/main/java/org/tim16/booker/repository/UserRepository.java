package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tim16.booker.model.utility.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query(value = "select * from users u where u.user_id =(select t.user from verification_tokens t where t.token = ?1)", nativeQuery = true)
    User findByToken(String token);
}

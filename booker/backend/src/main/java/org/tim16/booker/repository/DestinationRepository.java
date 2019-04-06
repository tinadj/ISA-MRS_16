package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tim16.booker.model.utility.Destination;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Integer> {

    Destination findByCityAndState(String city, String state);
}

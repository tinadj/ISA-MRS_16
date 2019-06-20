package org.tim16.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tim16.booker.model.airline.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {

    Airline findByName(String name);
}
package com.onereach.legalbot.infrastructure.repository;

import com.onereach.legalbot.infrastructure.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    // Additional query methods can be defined here
}
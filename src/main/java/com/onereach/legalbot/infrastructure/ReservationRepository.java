/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.infrastructure;

import com.onereach.legalbot.infrastructure.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangdaini
 * @version ReservationRepository.java, v 0.1 2024年08月27日 12:01 am wangdaini
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
package com.onereach.legalbot.infrastructure.repository;

import com.onereach.legalbot.infrastructure.model.PartnerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerUserRepository extends JpaRepository<PartnerUser, Integer> {
    // Additional query methods can be defined here

    PartnerUser findByUserName(String userName);
}
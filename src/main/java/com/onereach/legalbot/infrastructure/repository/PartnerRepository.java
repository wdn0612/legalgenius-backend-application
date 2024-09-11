package com.onereach.legalbot.infrastructure.repository;

import com.onereach.legalbot.infrastructure.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Integer> {

    Partner findByPartnerName(String partnerName);
}
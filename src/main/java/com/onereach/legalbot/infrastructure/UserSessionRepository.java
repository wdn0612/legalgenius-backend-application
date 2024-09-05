/*
 * DN
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.infrastructure;

import com.onereach.legalbot.infrastructure.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangdaini
 * @version UserInfoRepository.java, v 0.1 2024年08月30日 9:45 pm wangdaini
 */
@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
}
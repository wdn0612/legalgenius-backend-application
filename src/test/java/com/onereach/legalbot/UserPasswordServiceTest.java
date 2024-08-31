/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot;

/**
 *
 * @author wangdaini
 * @version UserPasswordServiceTest.java, v 0.1 2024年08月31日 11:18 pm wangdaini
 */

import com.onereach.legalbot.core.service.UserPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class UserPasswordServiceTest {

    private UserPasswordService userPasswordService;

    @BeforeEach
    public void setUp() {
        userPasswordService = new UserPasswordService();
    }

    @Test
    public void testEncode() {
        // Given
        String rawPassword = "secretPassword";

        // When
        String encodedPassword = userPasswordService.encode(rawPassword);

        log.info(encodedPassword);

        // Then
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(userPasswordService.matches(rawPassword, encodedPassword));
    }
}

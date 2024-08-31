/*
 * DN
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordService implements PasswordEncoder {

    private final PasswordEncoder passwordEncoder;

    public UserPasswordService() {
        // You can adjust the strength parameter to increase security (e.g., 12)
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.service.impl;

import com.dntech.budgetplanningapp.service.EmailService;
import org.springframework.stereotype.Service;

/**
 * 电邮实现类
 *
 * @author wangdaini
 * @version EmailServiceImpl.java, v 0.1 2024年02月16日 3:16 pm wangdaini
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public boolean send(String text) {
        System.out.println("text");
        return false;
    }
}
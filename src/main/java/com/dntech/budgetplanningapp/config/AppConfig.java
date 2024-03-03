/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangdaini
 * @version AppConfig.java, v 0.1 2024年02月23日 6:38 pm wangdaini
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.dntech.budgetplanningapp.infrastructure")
public class AppConfig {
    // Add more configs here
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
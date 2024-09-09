/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

/**
 * 应用基本配置
 *
 * @author wangdaini
 * @version AppConfig.java, v 0.1 2024年02月23日 6:38 pm wangdaini
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.onereach.legalbot.infrastructure")
@EnableAsync
public class AppConfig {

    @Value("${isSandBox:}")
    public String IsSandBox = "0";

    @Value("${app.env}")
    public String appEnv = "test";

    @Value("${douyin.openapi.appid}")
    public String douyinOpenApiAppid;

    @Value("${douyin.openapi.appsecret}")
    public String douyinOpenApiAppsecret;

    @Value("${douyin.openapi.domain}")
    public String douyinOpenApiDomain;

    @Value("${jwt.secret}")
    public String jwtSecret;

    @Value("${jwt.expiration.days}")
    public Long jwtExpirationDays;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("AsyncTask-");
        executor.initialize();
        return executor;
    }

}
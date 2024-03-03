/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 保持heroku活跃定时任务
 *
 * @author wangdaini
 * @version KeepAliveTask.java, v 0.1 2024年03月03日 8:55 pm wangdaini
 */
@Component
public class KeepAliveTask {

    private final RestTemplate restTemplate;

    @Autowired
    public KeepAliveTask(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 25 * 60 * 1000) // 25 minutes in milliseconds
    public void pingHerokuApp() {
        String url = "https://obscure-reaches-55832-205a2b956447.herokuapp.com/api/v1/hello";
        restTemplate.getForObject(url, String.class);
    }
}
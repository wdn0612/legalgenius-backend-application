package com.dntech.budgetplanningapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主应用入口
 *
 * @author wangdaini
 */
@SpringBootApplication
@RestController
@EnableScheduling
@Slf4j
public class BudgetPlanningAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetPlanningAppApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "Hello, this is an auto-budgeting app.";
    }

}

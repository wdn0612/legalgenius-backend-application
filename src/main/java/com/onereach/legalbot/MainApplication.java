package com.onereach.legalbot;

import com.onereach.legalbot.infrastructure.repository.ChatRecordRepository;
import jakarta.annotation.Resource;
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
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Resource
    private ChatRecordRepository chatRecordRepository;

    @GetMapping("/")
    public String hello() {
        return "Hello, this is legalbot.";
    }

}

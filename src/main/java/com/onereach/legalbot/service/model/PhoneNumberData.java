package com.onereach.legalbot.service.model;

import lombok.Data;

@Data
public class PhoneNumberData {
    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private Watermark watermark;

    // Getters and setters

    @Data
    public static class Watermark {
        private String appid;
        private long timestamp;

        // Getters and setters
    }
}
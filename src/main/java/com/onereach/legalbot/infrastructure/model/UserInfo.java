/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.infrastructure.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "platform_user_name", nullable = false, length = 50)
    private String platformUserName;

    @Column(name = "platform_user_id", nullable = false, length = 50)
    private String platformUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false, length = 50)
    private Platform platform;

    @Column(name = "platform_gender", length = 5)
    private String platformGender;

    @Column(name = "platform_city", length = 10)
    private String platformCity;

    @Column(name = "platform_province", length = 10)
    private String platformProvince;

    @Column(name = "platform_country", length = 10)
    private String platformCountry;

    @Column(name = "platform_avartar_url", length = 100)
    private String platformAvatarUrl;

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPlatformUserName() {
        return platformUserName;
    }

    public void setPlatformUserName(String platformUserName) {
        this.platformUserName = platformUserName;
    }

    public String getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(String platformUserId) {
        this.platformUserId = platformUserId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getPlatformGender() {
        return platformGender;
    }

    public void setPlatformGender(String platformGender) {
        this.platformGender = platformGender;
    }

    public String getPlatformCity() {
        return platformCity;
    }

    public void setPlatformCity(String platformCity) {
        this.platformCity = platformCity;
    }

    public String getPlatformProvince() {
        return platformProvince;
    }

    public void setPlatformProvince(String platformProvince) {
        this.platformProvince = platformProvince;
    }

    public String getPlatformCountry() {
        return platformCountry;
    }

    public void setPlatformCountry(String platformCountry) {
        this.platformCountry = platformCountry;
    }

    public String getPlatformAvatarUrl() {
        return platformAvatarUrl;
    }

    public void setPlatformAvatarUrl(String platformAvatarUrl) {
        this.platformAvatarUrl = platformAvatarUrl;
    }

    // Enum for platform values
    public enum Platform {
        DY_OPEN,
        DY_UNION
    }
}

/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.infrastructure.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_session")
public class UserSession {

    @Id
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "scene", nullable = false, length = 50)
    private SCENE scene;

    @Column(name = "scene_session_key", columnDefinition = "TEXT")
    private String sceneSessionKey;

    @Column(name = "token", length = 512)
    private String token;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public SCENE getScene() {
        return scene;
    }

    public void setScene(SCENE scene) {
        this.scene = scene;
    }

    public String getSceneSessionKey() {
        return sceneSessionKey;
    }

    public void setSceneSessionKey(String sceneSessionKey) {
        this.sceneSessionKey = sceneSessionKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Enum for platform values
    public enum SCENE {
        DY_MINIAPP
    }
}

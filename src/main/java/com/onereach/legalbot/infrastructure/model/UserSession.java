package com.onereach.legalbot.infrastructure.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.onereach.legalbot.infrastructure.model.enums.Scene;

@Entity
@Table(name = "user_session")
@Data
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private User user;

    @Column(name = "scene", length = 10)
    @Enumerated(EnumType.STRING)
    private Scene scene;

    @Column(name = "scene_session_key", columnDefinition = "TEXT")
    private String sceneSessionKey;

    @Column(length = 512)
    private String token;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    // Getters and setters
}
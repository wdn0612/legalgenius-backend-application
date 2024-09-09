package com.onereach.legalbot.infrastructure.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.onereach.legalbot.infrastructure.model.enums.Platform;

@Entity
@Table(name = "user_platform_account", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "platform", "platform_user_id" })
})
@Data
public class UserPlatformAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(length = 100)
    private String platformUserId;

    @Column(length = 100)
    private String platformUserName;

    @Column(length = 5)
    private String platformGender;

    @Column(length = 10)
    private String platformCity;

    @Column(length = 10)
    private String platformProvince;

    @Column(length = 10)
    private String platformCountry;

    @Column(length = 255)
    private String platformAvatarUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    // Getters and setters
}
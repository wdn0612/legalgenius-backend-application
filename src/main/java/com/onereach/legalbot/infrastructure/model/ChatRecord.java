package com.onereach.legalbot.infrastructure.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.onereach.legalbot.infrastructure.model.enums.Scene;
import com.onereach.legalbot.infrastructure.model.enums.FollowupStatus;

@Entity
@Table(name = "chat_record")
@Data
public class ChatRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Scene scene;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String messages;

    @Column
    private Boolean reservationIntent;

    @Column
    private Integer reservationId;

    @Column(length = 10)
    private String category;

    @Column(length = 256)
    private String summary;

    @Column
    private Integer priority;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private FollowupStatus followupStatus;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    // Getters and setters
}
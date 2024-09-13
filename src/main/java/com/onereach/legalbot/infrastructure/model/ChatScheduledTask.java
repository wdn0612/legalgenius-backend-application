package com.onereach.legalbot.infrastructure.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatScheduledTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "chat_id")
    private Integer chatId;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    public ChatScheduledTask(Integer chatId) {
        this.chatId = chatId;
    }
}
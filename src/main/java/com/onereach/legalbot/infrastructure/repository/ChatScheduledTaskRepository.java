package com.onereach.legalbot.infrastructure.repository;

import com.onereach.legalbot.infrastructure.model.ChatScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatScheduledTaskRepository extends JpaRepository<ChatScheduledTask, Integer> {
    List<ChatScheduledTask> findAllByScheduledAtAfter(LocalDateTime time);

    Optional<ChatScheduledTask> findByChatId(Integer chatId);
}
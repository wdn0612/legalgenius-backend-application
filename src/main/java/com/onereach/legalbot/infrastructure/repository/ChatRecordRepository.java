package com.onereach.legalbot.infrastructure.repository;

import com.onereach.legalbot.infrastructure.model.ChatRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRecordRepository extends JpaRepository<ChatRecord, Integer> {

    ChatRecord findByChatId(Integer chatId);

    // Use @Query annotation to define the query explicitly
    List<ChatRecord> findByUser_UserIdOrderByCreatedAtDesc(Integer userId);
}
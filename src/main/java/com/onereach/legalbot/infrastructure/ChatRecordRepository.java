/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.infrastructure;

import com.onereach.legalbot.infrastructure.model.ChatRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 聊天记录仓库
 *
 * @author wangdaini
 * @version ChatRecordRepository.java, v 0.1 2024年08月25日 9:46 pm wangdaini
 */
@Repository
public interface ChatRecordRepository extends JpaRepository<ChatRecord, Integer> {
    ChatRecord findByChatId(Integer chatId);

    List<ChatRecord> findByUserIdOrderByCreatedAtDesc(Integer userId);
}
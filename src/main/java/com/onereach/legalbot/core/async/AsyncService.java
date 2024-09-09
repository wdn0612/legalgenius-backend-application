/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.async;

import com.onereach.legalbot.core.util.JsonUtil;
import com.onereach.legalbot.facade.model.Message;
import com.onereach.legalbot.infrastructure.repository.ChatRecordRepository;
import com.onereach.legalbot.infrastructure.model.ChatRecord;
import com.onereach.legalbot.service.ModelService;
import com.onereach.legalbot.service.request.CalculatePriorityRequest;
import com.onereach.legalbot.service.request.CategoryRequest;
import com.onereach.legalbot.service.request.SummaryRequest;
import com.onereach.legalbot.service.response.CalculatePriorityResponse;
import com.onereach.legalbot.service.response.CategoryResponse;
import com.onereach.legalbot.service.response.SummaryResponse;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author wangdaini
 * @version AsyncService.java, v 0.1 2024年08月27日 12:15 am wangdaini
 */
@Service
public class AsyncService {

    @Resource
    private ChatRecordRepository chatRecordRepository;

    @Resource
    private ModelService modelService;

    @Async
    public CompletableFuture<String> generatePostChatInfo(Integer conversationId) {
        return CompletableFuture.supplyAsync(() -> {

            ChatRecord chatRecord = chatRecordRepository.findByChatId(conversationId);
            String message = chatRecord.getMessage();
            List<Message> conversation = JsonUtil.jsonArrayToObjectList(message, Message.class);

            // 生成总结
            SummaryRequest summaryRequest = new SummaryRequest();
            summaryRequest.setSummaryType("CONVERSATION");
            summaryRequest.setMessageList(conversation);
            SummaryResponse summaryResponse = modelService.summarize(summaryRequest);
            String summary = summaryResponse.getSummary();

            // 生成标签
            CategoryRequest categoryRequest = new CategoryRequest();
            categoryRequest.setMessageList(conversation);
            CategoryResponse categoryResponse = modelService.categorize(categoryRequest);
            String category = categoryResponse.getCategory();

            // 生成优先级
            CalculatePriorityRequest priorityRequest = new CalculatePriorityRequest();
            priorityRequest.setMessageList(conversation);
            CalculatePriorityResponse calculatePriorityResponse = modelService.calculatePriority(priorityRequest);
            Integer priority = calculatePriorityResponse.getPriority();

            chatRecord.setPriority(priority);
            chatRecord.setCategory(category);
            chatRecord.setSummary(summary);

            chatRecordRepository.save(chatRecord);

            return "Finished post chat info generation";
        });
    }
}
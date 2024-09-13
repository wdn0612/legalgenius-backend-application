/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.async;

import com.onereach.legalbot.core.util.JsonUtil;
import com.onereach.legalbot.facade.model.Message;
import com.onereach.legalbot.infrastructure.model.ChatScheduledTask;
import com.onereach.legalbot.infrastructure.repository.ChatRecordRepository;
import com.onereach.legalbot.infrastructure.repository.ChatScheduledTaskRepository;
import com.onereach.legalbot.infrastructure.model.ChatRecord;
import com.onereach.legalbot.service.ModelService;
import com.onereach.legalbot.service.request.CalculatePriorityRequest;
import com.onereach.legalbot.service.request.CategoryRequest;
import com.onereach.legalbot.service.request.SummaryRequest;
import com.onereach.legalbot.service.response.CalculatePriorityResponse;
import com.onereach.legalbot.service.response.CategoryResponse;
import com.onereach.legalbot.service.response.SummaryResponse;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author wangdaini
 * @version AsyncService.java, v 0.1 2024年08月27日 12:15 am wangdaini
 */
@Service
public class AsyncService {

    @Resource
    private ChatRecordRepository chatRecordRepository;

    @Resource
    private ChatScheduledTaskRepository chatScheduledTaskRepository;

    @Resource
    private ModelService modelService;

    @Resource
    private TaskScheduler taskScheduler;

    private final Map<Integer, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();

    @PostConstruct
    public void initializeScheduledTasks() {
        List<ChatScheduledTask> tasks = chatScheduledTaskRepository.findAllByScheduledAtAfter(LocalDateTime.now());
        for (ChatScheduledTask task : tasks) {
            schedulePostChatInfoGeneration(task.getChatId(), task.getScheduledAt());
        }
    }

    @Async
    public CompletableFuture<String> generatePostChatInfo(Integer conversationId) {
        return CompletableFuture.supplyAsync(() -> {

            ChatRecord chatRecord = chatRecordRepository.findByChatId(conversationId);
            String message = chatRecord.getMessages();
            List<Message> conversation = JsonUtil.jsonArrayToObjectList(message, Message.class);

            // 生成总结
            SummaryRequest summaryRequest = new SummaryRequest();
            summaryRequest.setSummaryType("CASE");
            summaryRequest.setMessages(conversation);
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
            String reason = calculatePriorityResponse.getReason();

            chatRecord.setPriority(priority);
            chatRecord.setCategory(category);
            chatRecord.setSummary(summary);
            chatRecord.setReason(reason);

            chatRecordRepository.save(chatRecord);

            return "Finished post chat info generation";
        });
    }

    public void updateLastActivityTime(Integer conversationId) {
        LocalDateTime scheduledTime = LocalDateTime.now().plusMinutes(10);
        ScheduledFuture<?> existingTask = scheduledFutures.get(conversationId);

        if (existingTask != null) {
            existingTask.cancel(false);
        }

        schedulePostChatInfoGeneration(conversationId, scheduledTime);

        ChatScheduledTask task = chatScheduledTaskRepository.findByChatId(conversationId).orElse(
                new ChatScheduledTask(conversationId));
        task.setScheduledAt(scheduledTime);
        chatScheduledTaskRepository.save(task);
    }

    @Async
    private void schedulePostChatInfoGeneration(Integer conversationId, LocalDateTime scheduledTime) {
        ZonedDateTime zonedScheduledTime = scheduledTime.atZone(ZoneId.systemDefault());

        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(
                () -> generatePostChatInfo(conversationId),
                zonedScheduledTime.toInstant());

        scheduledFutures.put(conversationId, scheduledTask);
    }

}
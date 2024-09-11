/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.impl;

import com.onereach.legalbot.core.util.JsonUtil;
import com.onereach.legalbot.service.ModelService;
import com.onereach.legalbot.service.request.CalculatePriorityRequest;
import com.onereach.legalbot.service.request.CategoryRequest;
import com.onereach.legalbot.service.request.CompletionRequest;
import com.onereach.legalbot.service.request.SummaryRequest;
import com.onereach.legalbot.service.response.CalculatePriorityResponse;
import com.onereach.legalbot.service.response.CategoryResponse;
import com.onereach.legalbot.service.response.CompletionResponse;
import com.onereach.legalbot.service.response.SummaryResponse;
import com.onereach.legalbot.service.template.IntegrationTemplate;
import com.onereach.legalbot.service.template.IntegrationCallback;
import com.onereach.legalbot.config.AppConfig;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangdaini
 * @version ModelServiceImpl.java, v 0.1 2024年08月26日 6:36 pm wangdaini
 */
@Slf4j
@Component
public class ModelServiceImpl implements ModelService {

    private final RestTemplate restTemplate;

    @Autowired
    private AppConfig appConfig;

    public ModelServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public SummaryResponse summarize(SummaryRequest request) {

        return IntegrationTemplate.invoke(new IntegrationCallback<SummaryResponse>() {

            @Override
            public SummaryResponse execute() {
                return callModelApi("/v1/chat/summary", request, SummaryResponse.class);
            }

            @Override
            public void handleFailResult(SummaryResponse result) {
                log.error("Failed to summarize: {}", result);
            }

            @Override
            public boolean success(SummaryResponse result) {
                return result != null && result.getResultStatus() == "S";
            }
        });

    }

    @Override
    public CategoryResponse categorize(CategoryRequest request) {
        return IntegrationTemplate.invoke(new IntegrationCallback<CategoryResponse>() {

            @Override
            public CategoryResponse execute() {
                return callModelApi("/v1/chat/category", request, CategoryResponse.class);
            }

            @Override
            public void handleFailResult(CategoryResponse result) {
                log.error("Failed to categorize: {}", result);
            }

            @Override
            public boolean success(CategoryResponse result) {
                return result != null && result.getResultStatus() == "S";
            }
        });
    }

    @Override
    public CompletionResponse complete(CompletionRequest request) {
        return IntegrationTemplate.invoke(new IntegrationCallback<CompletionResponse>() {

            @Override
            public CompletionResponse execute() {
                return callModelApi("/v1/chat/completions", request, CompletionResponse.class);
            }

            @Override
            public void handleFailResult(CompletionResponse result) {
                log.error("Failed to get completion: {}", result);
                throw new RuntimeException("Failed to get completion");
            }

            @Override
            public boolean success(CompletionResponse result) {
                // return result != null && result.getResultStatus() == "S";
                return true;
            }
        });
    }

    @Override
    public CalculatePriorityResponse calculatePriority(CalculatePriorityRequest request) {
        return IntegrationTemplate.invoke(new IntegrationCallback<CalculatePriorityResponse>() {

            @Override
            public CalculatePriorityResponse execute() {
                return callModelApi("/v1/chat/priority", request, CalculatePriorityResponse.class);
            }

            @Override
            public void handleFailResult(CalculatePriorityResponse result) {
                log.error("Failed to calculate priority: {}", result);
            }

            @Override
            public boolean success(CalculatePriorityResponse result) {
                return result != null && result.getResultStatus() == "S";
            }
        });
    }

    private <T> T callModelApi(String endpoint, Object request, Class<T> responseClazz) {

        // Create the URL by concatenating the base URL and the endpoint
        String url = appConfig.modelBaseUrl + endpoint;

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + appConfig.modelApiKey);
        headers.set("Accept-Charset", "UTF-8");

        // Create the request entity
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);

        // Make the HTTP POST request, passing the request entity
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class);

        log.info("Model API response: {}", response);
        // Return the response body
        return JsonUtil.fromJson(response.getBody(), responseClazz);
    }
}
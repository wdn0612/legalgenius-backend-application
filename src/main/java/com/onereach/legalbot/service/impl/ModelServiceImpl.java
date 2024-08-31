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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.onereach.legalbot.core.constant.CommonConstant.MODEL_DOMAIN_NAME;

/**
 * @author wangdaini
 * @version ModelServiceImpl.java, v 0.1 2024年08月26日 6:36 pm wangdaini
 */
@Slf4j
@Component
public class ModelServiceImpl implements ModelService {

    private final RestTemplate restTemplate;

    public ModelServiceImpl(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

    @Override
    public SummaryResponse summarize(SummaryRequest request) {
        String endpoint = "";
        String url = MODEL_DOMAIN_NAME + "";
        return restTemplate.postForObject(url, request, SummaryResponse.class);
    }

    @Override
    public CategoryResponse categorize(CategoryRequest request) {
        return null;
    }

    @Override
    public CompletionResponse complete(CompletionRequest request) {
        CompletionResponse completionResponse = new CompletionResponse();
        // TEST用;
        completionResponse.setSystemCompletion("system completion");
        completionResponse.setReservationIntent(false);
        return completionResponse;
    }

    @Override
    public CalculatePriorityResponse calculatePriority(CalculatePriorityRequest request) {
        return null;
    }

    private <T> T callModelApi(String endpoint, Object request, Class<T> responseClazz) {
        String url = "https://api.example.com/" + endpoint; //todo

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer 509572ce4e1404eef1458506204495385e4782c89b81525aba3c99a66793f17e"); //todo

        // Create the request entity
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);

        // Make the HTTP GET request, passing the request entity
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        // Return the response body
        return JsonUtil.fromJson(response.getBody(), responseClazz);
    }
}
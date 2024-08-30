/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service;

import com.onereach.legalbot.service.request.CalculatePriorityRequest;
import com.onereach.legalbot.service.request.CategoryRequest;
import com.onereach.legalbot.service.request.CompletionRequest;
import com.onereach.legalbot.service.request.SummaryRequest;
import com.onereach.legalbot.service.response.CalculatePriorityResponse;
import com.onereach.legalbot.service.response.CategoryResponse;
import com.onereach.legalbot.service.response.CompletionResponse;
import com.onereach.legalbot.service.response.SummaryResponse;

/**
 * @author wangdaini
 * @version ModelService.java, v 0.1 2024年08月26日 6:24 pm wangdaini
 */
public interface ModelService {

    SummaryResponse summarize(SummaryRequest request);
    CategoryResponse categorize(CategoryRequest request);

    CompletionResponse complete(CompletionRequest request);

    CalculatePriorityResponse calculatePriority(CalculatePriorityRequest request);
}
/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onereach.legalbot.facade.model.Message;
import com.onereach.legalbot.facade.request.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wangdaini
 * @version SummaryRequest.java, v 0.1 2024年08月26日 6:34 pm wangdaini
 */
@Setter
@Getter
public class SummaryRequest extends BaseRequest {

    /**
     * 总结类型 TITLE/CASE
     */
    @JsonProperty("summary_type")
    private String summaryType;

    private List<Message> messages;
}
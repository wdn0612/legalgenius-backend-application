/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wangdaini
 * @version CompletionResponse.java, v 0.1 2024年08月26日 11:13 pm wangdaini
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompletionResponse extends BaseResponse {

    @JsonProperty("message")
    private String systemCompletion;

    @JsonProperty("reservation_intent")
    private boolean reservationIntent;
}
/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.response;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author wangdaini
 * @version CalculatePriorityResponse.java, v 0.1 2024年08月27日 12:23 am wangdaini
 */
@Getter
@Setter
public class CalculatePriorityResponse extends BaseResponse {

    @JsonProperty("priority")
    private Integer priority;

    @JsonProperty("reason")
    private String reason;
}
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
 * @version CalculatePriorityRequest.java, v 0.1 2024年08月27日 12:23 am wangdaini
 */
@Getter
@Setter
public class CalculatePriorityRequest extends BaseRequest {

    @JsonProperty("messages")
    private List<Message> messageList;

}
/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.request;

import com.onereach.legalbot.facade.model.Message;
import lombok.Data;

import java.util.List;

/**
 * @author wangdaini
 * @version CalculatePriorityRequest.java, v 0.1 2024年08月27日 12:23 am wangdaini
 */
@Data
public class CalculatePriorityRequest {

    private List<Message> messageList;

}
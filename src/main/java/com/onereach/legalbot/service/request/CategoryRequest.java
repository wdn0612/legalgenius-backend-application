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
 * @version CategoryRequest.java, v 0.1 2024年08月26日 6:34 pm wangdaini
 */
@Data
public class CategoryRequest {
    private List<Message> messageList;
}
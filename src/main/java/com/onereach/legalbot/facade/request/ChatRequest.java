/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.request;

import com.onereach.legalbot.facade.model.Message;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 聊天请求
 *
 * @author wangdaini
 * @version ChatRequest.java, v 0.1 2024年08月19日 8:03 pm wangdaini
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest extends BaseRequest{

    private static final long serialVersionUID = -5483095475122785625L;

    private Integer conversationId;

    private Integer userId;

    @NotNull
    private List<Message> conversation;

    private String timestamp;

}
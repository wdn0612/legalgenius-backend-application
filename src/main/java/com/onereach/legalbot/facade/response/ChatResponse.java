/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 聊天响应
 *
 * @author wangdaini
 * @version ChatResponse.java, v 0.1 2024年08月25日 2:56 pm wangdaini
 */
@Getter
@Setter
@NoArgsConstructor
public class ChatResponse extends BaseResponse{

    private static final long serialVersionUID = -5808626584277840093L;

    private Integer conversationId;
    private String chatResponse;
    private boolean reservationIntent;
    private String title;
}
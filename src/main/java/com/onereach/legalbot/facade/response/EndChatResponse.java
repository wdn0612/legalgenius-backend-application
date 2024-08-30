/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 结束对话响应
 *
 * @author wangdaini
 * @version EndChatResponse.java, v 0.1 2024年08月26日 10:43 pm wangdaini
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndChatResponse extends BaseResponse{

    private Integer conversationId;
}
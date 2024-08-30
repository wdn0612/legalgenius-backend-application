/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 结束对话请求
 *
 * @author wangdaini
 * @version EndChatRequest.java, v 0.1 2024年08月26日 10:42 pm wangdaini
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndChatRequest extends BaseRequest {

    private static final long serialVersionUID = -8930963005227330594L;

    private Integer conversationId;
}
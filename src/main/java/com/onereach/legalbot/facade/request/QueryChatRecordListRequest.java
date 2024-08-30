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
 * @author wangdaini
 * @version QueryChatRecordListRequest.java, v 0.1 2024年08月30日 9:16 pm wangdaini
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryChatRecordListRequest extends BaseRequest{

    private Integer userId;
}
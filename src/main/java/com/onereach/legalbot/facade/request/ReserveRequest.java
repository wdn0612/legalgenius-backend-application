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
 * 预约请求
 *
 * @author wangdaini
 * @version ReserveRequest.java, v 0.1 2024年08月19日 8:09 pm wangdaini
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReserveRequest extends BaseRequest {

    private static final long serialVersionUID = 5646567498128091028L;

    private Integer conversationId;
    private String notes;

}
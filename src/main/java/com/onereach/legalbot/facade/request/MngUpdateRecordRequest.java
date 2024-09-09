/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.request;

import com.onereach.legalbot.infrastructure.model.enums.FollowupStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wangdaini
 * @version MngUpdateRecordRequest.java, v 0.1 2024年08月27日 10:51 pm wangdaini
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MngUpdateRecordRequest extends BaseRequest {

    private Integer conversationId;

    /**
     * 目前只有 RESERVED/CALLED
     */
    private FollowupStatus followupStatus;
    private String remark;
}
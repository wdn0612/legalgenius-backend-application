/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wangdaini
 * @version MngQueryRecordDetailRequest.java, v 0.1 2024年08月27日 9:40 pm wangdaini
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MngQueryRecordDetailRequest extends BaseRequest{

    private Integer conversationId;
}
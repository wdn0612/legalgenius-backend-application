/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.response;

import com.onereach.legalbot.facade.model.Result;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wangdaini
 * @version CompletionResponse.java, v 0.1 2024年08月26日 11:13 pm wangdaini
 */
@Getter
@Setter
public class CompletionResponse extends Result {

    private String systemCompletion;

    private boolean reservationIntent;
}
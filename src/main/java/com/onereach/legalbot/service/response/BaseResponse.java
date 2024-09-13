/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.response;

import com.onereach.legalbot.service.model.ModelResult;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author wangdaini
 * @version BaseResponse.java, v 0.1 2024年08月25日 2:56 pm wangdaini
 */
@Getter
@Setter
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 3110461203262829012L;

    @JsonProperty("result")
    private ModelResult result;
}
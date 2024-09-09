/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.response;

// import com.google.gson.annotations.SerializedName;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wangdaini
 * @version Code2SessionResponse.java, v 0.1 2024年08月31日 9:11 pm wangdaini
 */
@Getter
@Setter
public class Code2SessionResponse {

    @JsonProperty("err_no")
    private Integer errNo;

    @JsonProperty("err_tips")
    private String errTips;

    @JsonProperty("data")
    private Code2SessionResponseData data;
}
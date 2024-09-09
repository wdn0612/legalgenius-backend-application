/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.request;

/**
 *
 * @author wangdaini
 * @version Code2SessionRequest.java, v 0.1 2024年08月31日 9:07 pm wangdaini
 */
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Code2SessionRequest {
    @JsonProperty("appid")
    private String appid;

    @JsonProperty("secret")
    private String secret;

    @JsonProperty("code")
    private String code;

    @JsonProperty("anonymous_code")
    private String anonymousCode;
}
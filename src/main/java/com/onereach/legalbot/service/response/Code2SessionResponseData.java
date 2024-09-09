/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.response;

// import com.google.gson.annotations.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wangdaini
 * @version Code2SessionResponseData.java, v 0.1 2024年08月31日 9:11 pm wangdaini
 */
@Getter
@Setter
public class Code2SessionResponseData {

    @JsonProperty("session_key")
    private String sessionKey;

    @JsonProperty("openid")
    private String openid;

    @JsonProperty("anonymous_openid")
    private String anonymousOpenid;

    @JsonProperty("unionid")
    private String unionId;

    @JsonProperty("dopenid")
    private String dOpenId;
}
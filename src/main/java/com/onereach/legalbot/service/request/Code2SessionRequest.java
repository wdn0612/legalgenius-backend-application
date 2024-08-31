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
import com.google.gson.annotations.SerializedName;

public class Code2SessionRequest {
    @SerializedName("appid")
    private String appid;

    @SerializedName("secret")
    private String secret;

    @SerializedName("code")
    private String code;

    @SerializedName("anonymous_code")
    private String anonymousCode;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAnonymousCode() {
        return anonymousCode;
    }

    public void setAnonymousCode(String anonymousCode) {
        this.anonymousCode = anonymousCode;
    }
}
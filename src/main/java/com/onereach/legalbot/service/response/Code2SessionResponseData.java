/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.response;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author wangdaini
 * @version Code2SessionResponseData.java, v 0.1 2024年08月31日 9:11 pm wangdaini
 */
public class Code2SessionResponseData {

    @SerializedName("session_key")
    private String sessionKey;

    @SerializedName("openid")
    private String openid;

    @SerializedName("anonymous_openid")
    private String anonymousOpenid;

    @SerializedName("unionid")
    private String unionId;

    @SerializedName("dopenid")
    private String dOpenId;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAnonymousOpenid() {
        return anonymousOpenid;
    }

    public void setAnonymousOpenid(String anonymousOpenid) {
        this.anonymousOpenid = anonymousOpenid;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getdOpenId() {
        return dOpenId;
    }

    public void setdOpenId(String dOpenId) {
        this.dOpenId = dOpenId;
    }
}
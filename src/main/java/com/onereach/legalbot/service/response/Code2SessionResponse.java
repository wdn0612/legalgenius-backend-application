/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.response;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author wangdaini
 * @version Code2SessionResponse.java, v 0.1 2024年08月31日 9:11 pm wangdaini
 */
public class Code2SessionResponse {

    @SerializedName("err_no")
    private Integer errNo;

    @SerializedName("err_tips")
    private String errTips;

    @SerializedName("data")
    private Code2SessionResponseData data;

    public Integer getErrNo() {
        return errNo;
    }

    public void setErrNo(Integer errNo) {
        this.errNo = errNo;
    }

    public String getErrTips() {
        return errTips;
    }

    public void setErrTips(String errTips) {
        this.errTips = errTips;
    }

    public Code2SessionResponseData getData() {
        return data;
    }

    public void setData(Code2SessionResponseData data) {
        this.data = data;
    }
}
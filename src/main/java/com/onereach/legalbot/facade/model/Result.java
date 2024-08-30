/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用响应结果
 *
 * @author wangdaini
 * @version Result.java, v 0.1 2024年08月25日 9:41 pm wangdaini
 */
@Data
@NoArgsConstructor
public class Result implements Serializable {

    private static final long serialVersionUID = 7973371907432211564L;

    private String resultStatus;
    private String resultCode;
    private String resultMsg;

    public static Result success() {
        Result result = new Result();
        result.setResultStatus("S");
        result.setResultCode("success");
        return result;
    }

    public static Result fail() {
        Result result = new Result();
        result.setResultStatus("F");
        return result;
    }
}
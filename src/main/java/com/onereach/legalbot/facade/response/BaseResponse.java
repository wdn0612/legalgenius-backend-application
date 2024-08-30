/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.response;

import com.onereach.legalbot.facade.model.Result;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wangdaini
 * @version BaseResponse.java, v 0.1 2024年08月25日 2:56 pm wangdaini
 */
@Getter
@Setter
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 3110461203262829012L;
    private Result result;

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("BaseResponse [result=");
        builder.append(result);
        builder.append("]");
        return builder.toString();
    }
}
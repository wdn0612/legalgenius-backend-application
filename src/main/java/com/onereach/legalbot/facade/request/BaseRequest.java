/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础请求
 *
 * @author wangdaini
 * @version BaseRequest.java, v 0.1 2024年08月19日 8:01 pm wangdaini
 */
@Data
public class BaseRequest implements Serializable {

    /**
     * 场景
     */
    private String scene;

    /**
     * 机构
     */
    private String partner;
}
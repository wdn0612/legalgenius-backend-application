/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.model;

import lombok.Data;

/**
 * 复制base模版响应
 *
 * @author wangdaini
 * @version CopyBaseAppResponse.java, v 0.1 2024年02月15日 11:46 pm wangdaini
 */
@Data
public class CopyBaseAppResponse {

    private boolean success;
    private int errorCode;
    private String errorMsg;
    private String appToken;
    private String url;
    private String name;
    private String folderToken;
    private String defaultTableId;
    private String timeZone;
}
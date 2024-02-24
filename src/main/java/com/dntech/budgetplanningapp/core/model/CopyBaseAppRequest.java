/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.model;

import lombok.Data;

/**
 * 复制base模版请求
 *
 * @author wangdaini
 * @version CopyBaseAppRequest.java, v 0.1 2024年02月15日 11:42 pm wangdaini
 */
@Data
public class CopyBaseAppRequest {

    private String name;
    private String folderToken;
    private boolean withoutContent;
    private String timeZone;
}
/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.model;

import lombok.Data;

import java.util.Map;

/**
 * 单行记录值
 *
 * @author wangdaini
 * @version RecordVO.java, v 0.1 2024年02月16日 12:05 am wangdaini
 */
@Data
public class RecordVO {

    private String              recordId;
    private String              createdBy;
    private int                 createTime;
    private Map<String, String> keyValueMap;
}
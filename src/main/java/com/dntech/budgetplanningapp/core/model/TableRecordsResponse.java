/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.model;

import lombok.Data;

import java.util.List;

/**
 * 表格记录响应
 *
 * @author wangdaini
 * @version TableRecordsResponse.java, v 0.1 2024年02月16日 12:03 am wangdaini
 */
@Data
public class TableRecordsResponse {

    private boolean        hasMore;
    private String         pageToken;
    private int            totalNumber;
    private List<RecordVO> records;
}
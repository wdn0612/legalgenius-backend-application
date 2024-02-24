/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.model;

import lombok.Data;

import java.util.List;

/**
 * 获取app所有表格响应
 *
 * @author wangdaini
 * @version ListAppTablesResponse.java, v 0.1 2024年02月15日 11:59 pm wangdaini
 */
@Data
public class AppTablesResponse {

    private boolean success;
    private int errorCode;
    private String errorMsg;
    private Boolean hasMore;
    private String  pageToken;
    private Integer           totalNumber;
    private List<TableVO> tableVOS;
}
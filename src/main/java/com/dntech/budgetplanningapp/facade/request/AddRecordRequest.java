/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.facade.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 增加记账记录请求
 *
 * @author wangdaini
 * @version AddRecordRequest.java, v 0.1 2024年02月16日 2:05 pm wangdaini
 */
@Data
public class AddRecordRequest implements Serializable {

    private String authKey;
    private String amount;
    private String category;
    private String transactionType;
    private String paymentMethod;
    private String remark;
}
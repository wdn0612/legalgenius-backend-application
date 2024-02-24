/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.model;

import lombok.Data;

/**
 * 流水记录模型
 *
 * @author wangdaini
 * @version BudgetRecord.java, v 0.1 2024年02月15日 11:54 pm wangdaini
 */
@Data
public class BudgetRecord {

    private String amount;
    private String category;
    private String transactionType;
    private String paymentMethod;
    private String remark;
}
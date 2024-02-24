/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.facade.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 记账参数响应
 *
 * @author wangdaini
 * @version BudgetParamsResponse.java, v 0.1 2024年02月16日 2:07 pm wangdaini
 */
@Data
public class BudgetParamsResponse implements Serializable {

    private String       authKey;
    private List<String> transactionTypeList;
    private List<String> incomeCategoryList;
    private List<String> expenseCategoryList;
    private List<String> paymentMethodList;
}
/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.facade.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 申请记账请求
 *
 * @author wangdaini
 * @version ApplyVO.java, v 0.1 2024年02月15日 11:23 pm wangdaini
 */
@Data
public class BudgetApplyRequest implements Serializable {

    private static final long serialVersionUID = 7001010527078257754L;

    private String authKey;
}
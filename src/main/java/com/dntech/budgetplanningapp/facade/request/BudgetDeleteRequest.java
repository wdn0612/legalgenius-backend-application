/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.facade.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 记账删除请求
 *
 * @author wangdaini
 * @version BudgetDeleteRequest.java, v 0.1 2024年02月15日 11:26 pm wangdaini
 */
@Data
public class BudgetDeleteRequest implements Serializable {

    private static final long   serialVersionUID = -1096463356529877612L;

    private              String authKey;
}
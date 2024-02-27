/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.infrastructure.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 记账信息数据模型
 *
 * @author wangdaini
 * @version BudgetInfoDO.java, v 0.1 2024年02月16日 3:27 pm wangdaini
 */
@Data
@Document("UserInfo")
public class BudgetInfoDO {

    @Id
    private String authKey;
    private String email;
    private String fileUrl;
    private String appToken;
    private String transactionTableId;
}
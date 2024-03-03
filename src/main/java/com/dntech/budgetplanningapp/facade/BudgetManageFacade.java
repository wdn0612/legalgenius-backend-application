/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.facade;

import com.dntech.budgetplanningapp.facade.request.AddRecordRequest;
import com.dntech.budgetplanningapp.facade.response.BudgetParamsResponse;

/**
 *
 * 对外暴露的记账管理接口
 *
 * @author wangdaini
 * @version BudgetManageFacade.java, v 0.1 2024年02月15日 11:07 pm wangdaini
 */
public interface BudgetManageFacade {

    /**
     * 查询记账相关参数
     *
     * @param authKey
     * @return
     */
    BudgetParamsResponse queryBudgetParams(String authKey);

    /**
     * 创建记账记录
     *
     * @param addRecordRequest
     * @return
     */
    boolean addRecord(AddRecordRequest addRecordRequest);

    /**
     * 保持awake
     *
     * @return
     */
    String heartbeat();
}
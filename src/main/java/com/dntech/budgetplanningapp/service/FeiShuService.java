/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.service;

import com.dntech.budgetplanningapp.core.model.AppTablesResponse;
import com.dntech.budgetplanningapp.core.model.BudgetRecord;
import com.dntech.budgetplanningapp.core.model.CopyBaseAppRequest;
import com.dntech.budgetplanningapp.core.model.CopyBaseAppResponse;
import com.dntech.budgetplanningapp.core.model.FieldInfoResponse;

import java.util.List;

/**
 * 飞书API服务
 *
 * @author wangdaini
 * @version FeiShuService.java, v 0.1 2024年02月15日 11:08 pm wangdaini
 */
public interface FeiShuService {

    /**
     * 复制原有模版
     *
     * @param copyBaseAppRequest
     * @return
     */
    CopyBaseAppResponse copyBaseApp(CopyBaseAppRequest copyBaseAppRequest);

    /**
     * 更新公共权限
     *
     * @param appToken
     * @return
     */
    boolean updatePermissionPublic(String appToken, String tenantAccessToken);

    /**
     * 获取app的所有表格
     *
     * @param appToken
     * @return
     */
    AppTablesResponse listAppTables(String appToken);

    /**
     * 获取飞书tenant_access_token
     *
     * @param appId
     * @param appSecret
     * @return
     */
    String getTenantAccessToken(String appId, String appSecret);

    /**
     * 获取表格列名信息
     *
     * @param appToken
     * @param tableId
     * @return
     */
    List<FieldInfoResponse> listTableFields(String appToken, String tableId);

    /**
     * 创建流水记录
     *
     * @param appToken
     * @param tableId
     * @param budgetRecord
     * @return
     */
    boolean createBudgetRecord(String appToken, String tableId, BudgetRecord budgetRecord);
}
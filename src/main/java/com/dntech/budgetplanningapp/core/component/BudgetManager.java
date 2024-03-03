/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.component;

import com.dntech.budgetplanningapp.core.model.BudgetRecord;

import java.util.List;
import java.util.Map;

/**
 * 记账管理中心
 *
 * @author wangdaini
 * @version BudgetManager.java, v 0.1 2024年02月16日 3:01 pm wangdaini
 */
public interface BudgetManager {

    String create(String email) throws InterruptedException;

    boolean delete(String authKey);

    String queryInfoByAuthKey(String authKey);

    Map<String, List<String>> queryBudgetParams(String authKey);

    void addRecord(String authKey, BudgetRecord budgetRecord);

}
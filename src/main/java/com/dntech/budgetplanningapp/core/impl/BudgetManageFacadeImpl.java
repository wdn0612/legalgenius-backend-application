/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.impl;

import com.dntech.budgetplanningapp.core.component.BudgetManager;
import com.dntech.budgetplanningapp.core.model.BudgetRecord;
import com.dntech.budgetplanningapp.facade.BudgetManageFacade;
import com.dntech.budgetplanningapp.facade.request.AddRecordRequest;
import com.dntech.budgetplanningapp.facade.response.BudgetParamsResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 记账管理入口实现类
 *
 * @author wangdaini
 * @version BudgeManageFacadeImpl.java, v 0.1 2024年02月16日 2:34 pm wangdaini
 */
@RestController
public class BudgetManageFacadeImpl implements BudgetManageFacade {

    @Resource
    private BudgetManager budgetManager;

    @GetMapping("/api/v1/budget/queryParams")
    @Override
    public BudgetParamsResponse queryBudgetParams(@RequestParam String authKey) {

        Map<String, List<String>> paramMap = budgetManager.queryBudgetParams(authKey);

        // TODO: 根据 paramMap 组装请求

        // TODO: errorHandling
        BudgetParamsResponse budgetParamsResponse = new BudgetParamsResponse();
        budgetParamsResponse.setAuthKey(authKey);
        budgetParamsResponse.setTransactionTypeList(paramMap.get("transactiontype"));
        budgetParamsResponse.setExpenseCategoryList(paramMap.get("category"));
        budgetParamsResponse.setPaymentMethodList(paramMap.get("paymentmethod"));

        return budgetParamsResponse;
    }

    @PostMapping("/api/v1/budget/addRecord")
    @Override
    public boolean addRecord(@RequestBody AddRecordRequest addRecordRequest) {

        BudgetRecord budgetRecord = new BudgetRecord();
        budgetRecord.setAmount(addRecordRequest.getAmount());
        budgetRecord.setCategory(addRecordRequest.getCategory());
        budgetRecord.setTransactionType(addRecordRequest.getTransactionType());
        budgetRecord.setPaymentMethod(addRecordRequest.getPaymentMethod());
        budgetRecord.setRemark(addRecordRequest.getRemark());

        // TODO: Error Handling
        budgetManager.addRecord(addRecordRequest.getAuthKey(), budgetRecord);
        return true;
    }
}
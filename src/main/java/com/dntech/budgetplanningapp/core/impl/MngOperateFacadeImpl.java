/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.impl;

import com.dntech.budgetplanningapp.core.component.BudgetManager;
import com.dntech.budgetplanningapp.facade.MngOperateFacade;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 运营操作实现类
 *
 * @author wangdaini
 * @version MngOperateFacadeImpl.java, v 0.1 2024年02月16日 1:53 pm wangdaini
 */
@Slf4j
@RestController
public class MngOperateFacadeImpl implements MngOperateFacade {

    @Resource
    private BudgetManager budgetManager;

    /**
     * 新用户申请
     *
     * @param email
     * @return
     */
    @GetMapping("/api/v1/mng/apply")
    @Override
    public boolean apply(String email) {
        log.info("提交记账申请, email: " + email);
        try {
            budgetManager.create(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 用户注销
     *
     * @param authKey
     * @return
     */
    @GetMapping("/api/v1/mng/delete")
    @Override
    public boolean delete(String authKey) {
        log.info("删除记账申请, authKey: " + authKey);
        budgetManager.delete(authKey);
        return true;
    }

    /**
     * 查询用户绑定信息
     *
     * @param authKey
     * @return
     */
    @GetMapping("/api/v1/mng/query")
    @Override
    public String query(String authKey) {
        log.info("查询记账申请详情，authKey: " + authKey);
        return budgetManager.queryInfoByAuthKey(authKey);
    }

}
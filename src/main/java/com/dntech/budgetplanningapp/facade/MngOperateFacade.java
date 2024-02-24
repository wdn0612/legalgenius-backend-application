/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.facade;

import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * 对外暴露的运营操作接口
 *
 * @author wangdaini
 * @version MngOperateFacade.java, v 0.1 2024年02月15日 11:03 pm wangdaini
 */
public interface MngOperateFacade {

    /**
     * 申请记账入口
     *
     * @param authKey
     * @return
     */
    boolean apply(@RequestParam String email);

    /**
     * 删除记账记录入口
     *
     * @param authKey
     * @return
     */
    boolean delete(@RequestParam String authKey);

    /**
     * 查询记账账户绑定详情
     *
     * @param authKey
     * @return
     */
    String query(@RequestParam String authKey);
}
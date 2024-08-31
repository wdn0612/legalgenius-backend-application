/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.template;

import lombok.extern.slf4j.Slf4j;

/**
 * API请求通用接口处理
 * // TODO 后续refactor
 *
 * @author wangdaini
 * @version Template.java, v 0.1 2024年08月25日 9:30 pm wangdaini
 */
@Slf4j
public class ApiProcessTemplate {


    public static <T, R> T execute(R request, ApiProcessFunction<T> function) {
        T result = null;
        try {
            result = function.execute();
        } catch (Exception e) {
            log.error("processing request: {} got exception {}", request, e.getMessage());
            result = function.handleException(result, e);
        }
        return result;
    }

}
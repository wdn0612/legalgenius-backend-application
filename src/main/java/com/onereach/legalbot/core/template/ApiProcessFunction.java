/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.template;

/**
 * API process功能
 *
 * @author wangdaini
 * @version ApiProcessCallback.java, v 0.1 2024年08月26日 10:36 am wangdaini
 */
public interface ApiProcessFunction<T> {

    T execute() throws Exception;

    T handleException(T result, Exception e);

}
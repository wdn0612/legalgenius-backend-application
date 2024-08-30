/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.template;

/**
 * @author wangdaini
 * @version IntegrationCallback.java, v 0.1 2024年02月17日 11:25 pm wangdaini
 */
public interface IntegrationCallback<T> {

    T execute() throws Exception;

    void handleFailResult(T result);

    boolean success(T result);
}
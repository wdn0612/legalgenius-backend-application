/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.template;

/**
 * @author wangdaini
 * @version IntegrationTemplate.java, v 0.1 2024年02月17日 11:23 pm wangdaini
 */
public class IntegrationTemplate {

    public static <T> T invoke(IntegrationCallback<T> callback) {
        T result = null;
        try {
            result = callback.execute();
            if (!callback.success(result)) {
                callback.handleFailResult(result);
            }
        } catch (Exception e) {
            callback.handleFailResult(result);
        }
        return result;
    }
}
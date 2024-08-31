/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * 处理json文本工具类
 *
 * @author wangdaini
 * @version JsonUtil.java, v 0.1 2024年08月26日 11:00 pm wangdaini
 */
public class JsonUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static <T> String listToJsonArrayStr(List<T> list) {
        String jsonArray = null;
        try {
            jsonArray = OBJECT_MAPPER.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("error creating json array string from list %s", list));
        }
        return jsonArray;
    }

    public static <T> List<T> jsonArrayToObjectList(String jsonArrayString, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonArrayString, new TypeReference<List<T>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("error converting json array string to list of objects %s", jsonArrayString));
        }
    }

    public static String toJson(Object obj) {
        try {
            String s = OBJECT_MAPPER.writeValueAsString(obj);
            return s;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("error converting json string from object %s", obj.toString()));
        }
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON string to object", e);
        }
    }


}
/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 信息
 *
 * @author wangdaini
 * @version Message.java, v 0.1 2024年08月25日 9:25 pm wangdaini
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    /**
     * 目前只有 user、system
     */
    private String sender;

    /**
     * 消息
     */
    private String message;
}
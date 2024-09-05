/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chencheng
 * @version DouyinLoginRequest.java, v 0.1 2024年08月30日 8:44 pm chencheng
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DouyinLoginRequest extends BaseRequest {

    private String code;
}
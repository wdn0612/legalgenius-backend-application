/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author wangdaini
 * @version LoginResponse.java, v 0.1 2024年08月31日 9:05 pm wangdaini
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DouyinLoginResponse extends BaseResponse {

    private String openId;
    private String unionId;
    private String token;
}
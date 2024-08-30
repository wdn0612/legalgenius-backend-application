/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * 预约响应
 *
 * @author wangdaini
 * @version ReserveResponse.java, v 0.1 2024年08月25日 2:57 pm wangdaini
 */
@Getter
@Setter
@NoArgsConstructor
public class ReserveResponse extends BaseResponse{

    private static final long serialVersionUID = 5364882761254844107L;

    private Integer reservationId;
}
/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.response;

import com.onereach.legalbot.facade.model.Result;
import lombok.Data;

/**
 * @author wangdaini
 * @version SummaryResponse.java, v 0.1 2024年08月26日 6:35 pm wangdaini
 */
@Data
public class SummaryResponse extends Result {

    private String summary;
}
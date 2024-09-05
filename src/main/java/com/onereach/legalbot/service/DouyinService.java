/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service;

import com.onereach.legalbot.service.request.Code2SessionRequest;
import com.onereach.legalbot.service.response.Code2SessionResponse;

/**
 *
 * @author wangdaini
 * @version TikTokService.java, v 0.1 2024年08月31日 10:36 pm wangdaini
 */
public interface DouyinService {

    Code2SessionResponse code2Session(Code2SessionRequest code2SessionRequest);
}
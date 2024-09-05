/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.impl;

import com.onereach.legalbot.config.AppConfig;
import com.onereach.legalbot.service.DouyinService;
import com.onereach.legalbot.service.request.Code2SessionRequest;
import com.onereach.legalbot.service.response.Code2SessionResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author wangdaini
 * @version TikTokServiceImpl.java, v 0.1 2024年08月31日 10:37 pm wangdaini
 */
@Slf4j
@Component
public class DouyinServiceImpl implements DouyinService {

    @Resource
    private AppConfig appConfig;

    @Override
    public Code2SessionResponse code2Session(Code2SessionRequest code2SessionRequest) {
        // code2session的http请求 TODO 常量化
        // String domain = "https://developer.toutiao.com";
        // if (appConfig.IsSandBox.equals("1")) {
        // domain = "https://open-sandbox.douyin.com";
        // }
        //
        // String code2SessionPath = "/api/apps/v2/jscode2session";
        // Code2SessionResponse response = HttpUtil.HttpPost(code2SessionPath,
        // gson.toJson(code2SessionRequest), "https", domain,
        // Code2SessionResponse.class);

        return null;
    }
}
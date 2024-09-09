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

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    private final RestTemplate restTemplate;

    public DouyinServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Code2SessionResponse code2Session(Code2SessionRequest code2SessionRequest) {

        String domain = appConfig.douyinOpenApiDomain;
        String appid = appConfig.douyinOpenApiAppid;
        String secret = appConfig.douyinOpenApiAppsecret;

        String code2SessionPath = "/api/apps/v2/jscode2session";
        code2SessionRequest.setAppid(appid);
        code2SessionRequest.setSecret(secret);

        // 向抖音服务器发送请求
        Code2SessionResponse code2SessionResponse = restTemplate
                .postForEntity(domain + code2SessionPath, code2SessionRequest, Code2SessionResponse.class).getBody();
        return code2SessionResponse;
    }
}
/*
 * DN
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.service;

import com.onereach.legalbot.infrastructure.PartnerMngUserInfoRepository;
import com.onereach.legalbot.infrastructure.model.PartnerMngUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author wangdaini
 * @version AuthService.java, v 0.1 2024年08月30日 8:45 pm wangdaini
 */
@Service
@Slf4j
public class AuthService {
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserPasswordService userPasswordService;

    @Autowired
    private PartnerMngUserInfoRepository partnerMngUserInfoRepository;

    public String mngLogin(String username, String password) {
        // 验证用户名和密码
        if (isValidUser(username, password)) {
            // 生成 token
            String token = UUID.randomUUID().toString();

            log.info("生成token, token: {} | user: {}", token, username);
            // 将 token 存储在 Caffeine 缓存中，缓存名为 "tokens"
            cacheManager.getCache("tokens").put(token, username);

            return token;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public String getAccessToken(String userId) {
        // 生成 token
        String token = UUID.randomUUID().toString();

        // 将 token 存储在 Caffeine 缓存中，缓存名为 "tokens"
        cacheManager.getCache("tokens").put(token, userId);
        return token;
    }

    public boolean validateAndRefresh(String token) {
        Cache cache = cacheManager.getCache("tokens");
        if (cache != null && cache.get(token) != null) {
            // Refresh the token's expiration time by re-adding it to the cache
            String username = (String) cache.get(token).get();
            cache.evict(token);
            cache.put(token, username);
            return true;
        }
        throw new RuntimeException("invalid token");
    }

    private boolean isValidUser(String username, String password) {
        // 这里可以是数据库查询或其他验证逻辑
        PartnerMngUserInfo mngUserInfo = partnerMngUserInfoRepository.findByUserName(username);
        return userPasswordService.matches(password, mngUserInfo.getPasswordHash());

    }
}
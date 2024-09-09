/*
 * DN
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.service;

import com.onereach.legalbot.infrastructure.repository.PartnerUserRepository;
import com.onereach.legalbot.infrastructure.model.PartnerUser;
import com.onereach.legalbot.infrastructure.model.User;
import com.onereach.legalbot.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import java.util.UUID;
import java.util.Date;

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
    private PartnerUserRepository partnerUserRepository;

    @Autowired
    private AppConfig appConfig;

    public String mngLogin(String username, String password) {
        // 验证用户名和密码
        if (isValidUser(username, password)) {
            // 生成 token
            String token = UUID.randomUUID().toString();

            log.info("生成token, token: {} | user: {}", token, username);
            // 将 token 存储在 Caffeine 缓存中，缓存名为 "tokens"
            Cache cache = cacheManager.getCache("tokens");
            if (cache != null) {
                cache.put(token, username);
            } else {
                throw new RuntimeException("Cache is null");
            }

            return token;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    // public String getAccessToken(String userId) {
    // // 生成 token
    // String token = UUID.randomUUID().toString();

    // // 将 token 存储在 Caffeine 缓存中，缓存名为 "tokens"
    // cacheManager.getCache("tokens").put(token, userId);
    // return token;
    // }

    public String generateTokenForUser(User user) {
        Long jwtExpirationDays = appConfig.jwtExpirationDays;
        String jwtSecret = appConfig.jwtSecret;

        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpirationDays * 24L * 60L * 60L * 1000L);

        log.info("Generating token for user: {}", user.getUserId());
        log.info("Token expiration: {}", expiration);

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setSubject(user.getUserId().toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(appConfig.jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.error("Token expired", e);
            return false;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            log.error("Invalid token signature", e);
            return false;
        } catch (Exception e) {
            log.error("Token validation error", e);
            return false;
        }
    }

    public Integer getUserIdFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(appConfig.jwtSecret.getBytes());
        return Integer
                .parseInt(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject());
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
        PartnerUser partnerUser = partnerUserRepository.findByUserName(username);
        return userPasswordService.matches(password, partnerUser.getPasswordHash());

    }
}
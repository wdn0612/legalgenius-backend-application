/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class WebFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
                                                                                                                       ServletException,
                                                                                                                       IOException {
        filterChain.doFilter(request, new HttpServletResponseWrapper(response) {
            @Override
            public void setContentLength(int len) {
                // do nothing
            }
            @Override
            public void setHeader(String name, String value) {
                if (!"Content-Length".equalsIgnoreCase(name)) {
                    super.setHeader(name, value);
                }
            }
        });
    }
}
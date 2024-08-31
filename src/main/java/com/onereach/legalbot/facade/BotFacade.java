/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade;

import com.onereach.legalbot.facade.request.ChatRequest;
import com.onereach.legalbot.facade.request.EndChatRequest;
import com.onereach.legalbot.facade.request.LoginRequest;
import com.onereach.legalbot.facade.request.QueryChatRecordListRequest;
import com.onereach.legalbot.facade.request.ReserveRequest;
import com.onereach.legalbot.facade.response.ChatResponse;
import com.onereach.legalbot.facade.response.EndChatResponse;
import com.onereach.legalbot.facade.response.TikTokLoginResponse;
import com.onereach.legalbot.facade.response.QueryChatRecordListResponse;
import com.onereach.legalbot.facade.response.ReserveResponse;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

/**
 * 面向用户的接口
 *
 * @author wangdaini
 * @version UserFacade.java, v 0.1 2024年08月25日 2:58 pm wangdaini
 */
public interface BotFacade {

    ResponseEntity<TikTokLoginResponse> login(RequestEntity<LoginRequest> loginRequest);

    /**
     * 聊天
     * @param httpRequest
     * @return
     */
    ResponseEntity<ChatResponse> chat(RequestEntity<ChatRequest> httpRequest);

    /**
     * 预约
     * @param httpRequest
     * @return
     */
    ResponseEntity<ReserveResponse> reserve(RequestEntity<ReserveRequest> httpRequest);

    /**
     * 结束对话
     *
     * @param endChatRequest
     * @return
     */
    ResponseEntity<EndChatResponse> endChat(RequestEntity<EndChatRequest> endChatRequest);

    /**
     * 对客查询历史聊天
     *
     * @param request
     * @return
     */
    ResponseEntity<QueryChatRecordListResponse> queryChatRecordList(RequestEntity<QueryChatRecordListRequest> request);
}
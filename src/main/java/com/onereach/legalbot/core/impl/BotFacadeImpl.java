/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.impl;

import com.onereach.legalbot.core.async.AsyncService;
import com.onereach.legalbot.core.template.ApiProcessFunction;
import com.onereach.legalbot.core.template.ApiProcessTemplate;
import com.onereach.legalbot.core.util.ConvertUtil;
import com.onereach.legalbot.core.util.JsonUtil;
import com.onereach.legalbot.facade.BotFacade;
import com.onereach.legalbot.facade.model.ChatRecordVO;
import com.onereach.legalbot.facade.model.Message;
import com.onereach.legalbot.facade.model.Result;
import com.onereach.legalbot.facade.request.ChatRequest;
import com.onereach.legalbot.facade.request.EndChatRequest;
import com.onereach.legalbot.facade.request.QueryChatRecordListRequest;
import com.onereach.legalbot.facade.request.ReserveRequest;
import com.onereach.legalbot.facade.response.ChatResponse;
import com.onereach.legalbot.facade.response.EndChatResponse;
import com.onereach.legalbot.facade.response.QueryChatRecordListResponse;
import com.onereach.legalbot.facade.response.ReserveResponse;
import com.onereach.legalbot.infrastructure.ChatRecordRepository;
import com.onereach.legalbot.infrastructure.ReservationRepository;
import com.onereach.legalbot.infrastructure.model.ChatRecord;
import com.onereach.legalbot.infrastructure.model.Reservation;
import com.onereach.legalbot.service.ModelService;
import com.onereach.legalbot.service.request.CompletionRequest;
import com.onereach.legalbot.service.request.SummaryRequest;
import com.onereach.legalbot.service.response.CompletionResponse;
import com.onereach.legalbot.service.response.SummaryResponse;
import jakarta.annotation.Resource;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.onereach.legalbot.core.constant.CommonConstant.SYSTEM;

/**
 * 用户接口实现类
 *
 * @author wangdaini
 * @version UserFacadeImpl.java, v 0.1 2024年08月25日 9:24 pm wangdaini
 */
@RestController
public class BotFacadeImpl implements BotFacade {

    @Resource
    private ChatRecordRepository chatRecordRepository;

    @Resource
    private ReservationRepository reservationRepository;

    @Resource
    private ModelService modelService;

    @Resource
    private AsyncService asyncService;

    @Override
    @PostMapping(path = "/v1/chat")
    public ResponseEntity<ChatResponse> chat(RequestEntity<ChatRequest> httpRequest) {

        return ApiProcessTemplate.execute(httpRequest, new ApiProcessFunction<ResponseEntity<ChatResponse>>() {
            @Override
            public ResponseEntity<ChatResponse> execute() throws Exception {

                ChatRequest body = httpRequest.getBody();
                List<Message> conversation = body.getConversation();
                ChatRecord chatRecord;
                String title = null;

                if (body.getConversationId() != null) {
                    chatRecord = chatRecordRepository.findByChatId(body.getConversationId());
                    chatRecord.setMessage(JsonUtil.listToJsonArrayStr(conversation));
                    chatRecordRepository.save(chatRecord);
                } else {
                    // 初始化chatRecord
                    ChatRecord newChatRecord = new ChatRecord();
                    newChatRecord.setCreatedAt(LocalDateTime.now());
                    newChatRecord.setModifiedAt(LocalDateTime.now());
                    newChatRecord.setUserId(body.getUserId());
                    newChatRecord.setPartnerId(0); // TODO 目前API里没有
                    newChatRecord.setPlatform(""); // TODO 目前API里没有
                    newChatRecord.setMessage(JsonUtil.listToJsonArrayStr(conversation));
                    newChatRecord.setReservationIntent(false);

                    chatRecord = chatRecordRepository.save(newChatRecord);
                }

                if (conversation.size() == 3) {
                    SummaryRequest summaryRequest = new SummaryRequest();
                    summaryRequest.setSummaryType("TITLE");
                    summaryRequest.setMessageList(conversation);
                    SummaryResponse summaryResponse = modelService.summarize(summaryRequest);
                    title = summaryResponse.getSummary();
                }

                CompletionRequest completionRequest = new CompletionRequest();
                completionRequest.setMessageList(conversation);
                CompletionResponse completionResponse = modelService.complete(completionRequest);
                String completion = completionResponse.getSystemCompletion();
                conversation.add(new Message(SYSTEM, completion));
                chatRecord.setMessage(JsonUtil.listToJsonArrayStr(conversation));
                chatRecord.setReservationIntent(completionResponse.isReservationIntent());

                chatRecordRepository.save(chatRecord);

                // 组装返回结果
                ChatResponse chatResponse = new ChatResponse();
                chatResponse.setConversationId(chatRecord.getChatId());
                chatResponse.setChatResponse(completion);
                chatResponse.setReservationIntent(false);
                chatResponse.setTitle(title);
                chatResponse.setResult(Result.success());

                return new ResponseEntity<ChatResponse>(chatResponse, httpRequest.getHeaders(), 200);
            }

            @Override
            public ResponseEntity<ChatResponse> handleException(ResponseEntity<ChatResponse> result, Exception e) {
                ChatResponse chatResponse = new ChatResponse();
                Result resultResult = new Result();
                resultResult.setResultStatus("F");
                resultResult.setResultCode("FAIL");
                resultResult.setResultMsg(e.getMessage());
                chatResponse.setResult(resultResult);

                return new ResponseEntity<ChatResponse>(chatResponse, httpRequest.getHeaders(), 200);
            }
        });
    }

    @Override
    @PostMapping(path = "/v1/reserve")
    public ResponseEntity<ReserveResponse> reserve(RequestEntity<ReserveRequest> httpRequest) {
        return ApiProcessTemplate.execute(httpRequest, new ApiProcessFunction<ResponseEntity<ReserveResponse>>() {

            @Override
            public ResponseEntity<ReserveResponse> execute() throws Exception {

                ReserveRequest reserveRequest = httpRequest.getBody();
                Reservation reservation = new Reservation();
                reservation.setUserId(reserveRequest.getUserId());
                reservation.setChatId(reserveRequest.getConversationId());
                reservation.setReservationDate(LocalDateTime.now());
                reservation.setCreatedAt(LocalDateTime.now());

                Reservation savedReservation = reservationRepository.save(reservation);
                Integer reservationId = savedReservation.getReservationId();

                ChatRecord chatRecord = chatRecordRepository.findByChatId(reserveRequest.getConversationId());
                chatRecord.setReservationId(reservationId);
                chatRecordRepository.save(chatRecord);
                ReserveResponse reserveResponse = new ReserveResponse();
                reserveResponse.setResult(Result.success());
                reserveResponse.setReservationId(reservationId);
                return new ResponseEntity<ReserveResponse>(reserveResponse, httpRequest.getHeaders(), 200);
            }

            @Override
            public ResponseEntity<ReserveResponse> handleException(ResponseEntity<ReserveResponse> result, Exception e) {
                Result resultResult = new Result();
                ReserveResponse response = new ReserveResponse();

                resultResult.setResultStatus("F");
                resultResult.setResultCode("FAIL");
                resultResult.setResultMsg(e.getMessage());
                response.setResult(resultResult);

                return new ResponseEntity<ReserveResponse>(response, httpRequest.getHeaders(), 200);
            }
        });
    }

    @Override
    @PostMapping(path = "/v1/endChat")
    public ResponseEntity<EndChatResponse> endChat(RequestEntity<EndChatRequest> endChatRequest) {
        return ApiProcessTemplate.execute(endChatRequest, new ApiProcessFunction<ResponseEntity<EndChatResponse>>() {

            @Override
            public ResponseEntity<EndChatResponse> execute() throws Exception {
                asyncService.generatePostChatInfo(endChatRequest.getBody().getConversationId());

                EndChatResponse endChatResponse = new EndChatResponse();
                endChatResponse.setConversationId(endChatRequest.getBody().getConversationId());
                endChatResponse.setResult(Result.success());
                return new ResponseEntity<EndChatResponse>(endChatResponse, endChatRequest.getHeaders(), 200);
            }

            @Override
            public ResponseEntity<EndChatResponse> handleException(ResponseEntity<EndChatResponse> result, Exception e) {
                Result resultResult = new Result();
                EndChatResponse response = new EndChatResponse();

                resultResult.setResultStatus("F");
                resultResult.setResultCode("FAIL");
                resultResult.setResultMsg(e.getMessage());
                response.setResult(resultResult);
                return new ResponseEntity<EndChatResponse>(response, endChatRequest.getHeaders(), 200);
            }
        });
    }

    @Override
    @PostMapping("/v1/queryChatRecordList")
    public ResponseEntity<QueryChatRecordListResponse> queryChatRecordList(RequestEntity<QueryChatRecordListRequest> request) {
        return ApiProcessTemplate.execute(request, new ApiProcessFunction<ResponseEntity<QueryChatRecordListResponse>>() {
            @Override
            public ResponseEntity<QueryChatRecordListResponse> execute() throws Exception {
                List<ChatRecord> chatRecords = chatRecordRepository.findByUserIdOrderByCreatedAtDesc(
                        request.getBody().getUserId());
                List<ChatRecordVO> chatRecordVOList = chatRecords.stream().map(ConvertUtil::convertToChatRecordVO).collect(
                        Collectors.toList());
                QueryChatRecordListResponse queryChatRecordListResponse = new QueryChatRecordListResponse();
                queryChatRecordListResponse.setTotalRecords(chatRecordVOList.size());
                queryChatRecordListResponse.setRecords(chatRecordVOList);
                queryChatRecordListResponse.setResult(Result.success());
                return new ResponseEntity<QueryChatRecordListResponse>(queryChatRecordListResponse, request.getHeaders(), 200);
            }

            @Override
            public ResponseEntity<QueryChatRecordListResponse> handleException(ResponseEntity<QueryChatRecordListResponse> result,
                                                                               Exception e) {
                Result resultResult = new Result();
                QueryChatRecordListResponse response = new QueryChatRecordListResponse();

                resultResult.setResultStatus("F");
                resultResult.setResultCode("FAIL");
                resultResult.setResultMsg(e.getMessage());
                response.setResult(resultResult);

                return new ResponseEntity<QueryChatRecordListResponse>(response, request.getHeaders(), 200);
            }
        });
    };
}
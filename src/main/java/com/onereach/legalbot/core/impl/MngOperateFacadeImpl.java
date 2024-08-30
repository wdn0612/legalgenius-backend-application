/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.impl;

import com.onereach.legalbot.core.service.AuthService;
import com.onereach.legalbot.core.template.ApiProcessFunction;
import com.onereach.legalbot.core.template.ApiProcessTemplate;
import com.onereach.legalbot.core.util.ConvertUtil;
import com.onereach.legalbot.facade.MngOperateFacade;
import com.onereach.legalbot.facade.model.ChatRecordVO;
import com.onereach.legalbot.facade.model.Result;
import com.onereach.legalbot.facade.request.LoginRequest;
import com.onereach.legalbot.facade.request.MngQueryRecordDetailRequest;
import com.onereach.legalbot.facade.request.MngQueryRecordListRequest;
import com.onereach.legalbot.facade.request.MngUpdateRecordRequest;
import com.onereach.legalbot.facade.response.MngQueryRecordDetailResponse;
import com.onereach.legalbot.facade.response.MngQueryRecordListResponse;
import com.onereach.legalbot.facade.response.MngUpdateRecordResponse;
import com.onereach.legalbot.infrastructure.ChatRecordRepository;
import com.onereach.legalbot.infrastructure.model.ChatRecord;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 运营操作实现类
 *
 * @author wangdaini
 * @version MngOperateFacadeImpl.java, v 0.1 2024年02月16日 1:53 pm wangdaini
 */
@Slf4j
@RestController
public class MngOperateFacadeImpl implements MngOperateFacade {

    @Resource
    private ChatRecordRepository chatRecordRepository;

    @Resource
    private AuthService authService;

    @Override
    @PostMapping("/v1/mng/login")
    public ResponseEntity<String> login(LoginRequest request) {
        try {
            String token = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @Override
    @PostMapping("/v1/mng/queryRecordList")
    public ResponseEntity<MngQueryRecordListResponse> queryRecordList(RequestEntity<MngQueryRecordListRequest> request) {

        return ApiProcessTemplate.execute(request, new ApiProcessFunction<ResponseEntity<MngQueryRecordListResponse>>() {
            @Override
            public ResponseEntity<MngQueryRecordListResponse> execute() throws Exception {
                validateToken(request.getHeaders());

                MngQueryRecordListRequest body = request.getBody();
                String sortBy = Optional.ofNullable(body.getSortBy()).orElse("CreatedTime");
                String sortDir = Optional.ofNullable(body.getOrder()).orElse("DESC");
                switch (sortBy) {
                    case "ModifiedTime":
                        sortBy = "modifiedAt";
                        break;
                    case "Priority":
                        sortBy = "priority";
                        break;
                    default:
                        sortBy = "createdAt";
                        break;
                }
                Pageable pageable = PageRequest.of(body.getPageSize(), body.getCurrentPage(),
                        Sort.by(Sort.Direction.fromString(sortDir), sortBy));
                Page<ChatRecord> chatRecords = chatRecordRepository.findAll(pageable);

                MngQueryRecordListResponse mngQueryRecordListResponse = new MngQueryRecordListResponse();
                mngQueryRecordListResponse.setResult(Result.success());
                mngQueryRecordListResponse.setTotalRecords((int) chatRecords.getTotalElements());
                mngQueryRecordListResponse.setTotalPages(chatRecords.getTotalPages());
                mngQueryRecordListResponse.setCurrentPage(chatRecords.getNumber());
                mngQueryRecordListResponse.setRecordList(convertToChatVOList(chatRecords.getContent()));

                return new ResponseEntity<MngQueryRecordListResponse>(mngQueryRecordListResponse, request.getHeaders(), 200);
            }

            private List<ChatRecordVO> convertToChatVOList(List<ChatRecord> content) {
                return content.stream().map(ConvertUtil::convertToChatRecordVO).collect(Collectors.toList());
            }

            @Override
            public ResponseEntity<MngQueryRecordListResponse> handleException(ResponseEntity<MngQueryRecordListResponse> result,
                                                                              Exception e) {
                log.error(e.getLocalizedMessage());
                e.printStackTrace();
                Result resultResult = new Result();
                MngQueryRecordListResponse response = new MngQueryRecordListResponse();

                resultResult.setResultStatus("F");
                resultResult.setResultCode("FAIL");
                resultResult.setResultMsg(e.getMessage());
                response.setResult(resultResult);

                return new ResponseEntity<MngQueryRecordListResponse>(response, request.getHeaders(), 200);
            }

            ;
        });
    }

    private void validateToken(HttpHeaders headers) {
        String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
        String token = null;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
        }
        if (token == null) {
            throw new RuntimeException("Missing token");
        } else if (!authService.validateAndRefresh(token)) {
            throw new RuntimeException("Invalid token");
        }
    }

    @Override
    @PostMapping("/v1/mng/queryRecordDetail")
    public ResponseEntity<MngQueryRecordDetailResponse> queryRecordDetail(RequestEntity<MngQueryRecordDetailRequest> request) {
        return ApiProcessTemplate.execute(request, new ApiProcessFunction<ResponseEntity<MngQueryRecordDetailResponse>>() {

            @Override
            public ResponseEntity<MngQueryRecordDetailResponse> execute() throws Exception {

                validateToken(request.getHeaders());
                MngQueryRecordDetailRequest body = request.getBody();
                ChatRecord chatRecord = chatRecordRepository.findByChatId(body.getConversationId());
                ChatRecordVO chatRecordVO = ConvertUtil.convertToChatRecordVO(chatRecord);

                MngQueryRecordDetailResponse mngQueryRecordDetailResponse = new MngQueryRecordDetailResponse();
                mngQueryRecordDetailResponse.setRecord(chatRecordVO);
                mngQueryRecordDetailResponse.setResult(Result.success());

                log.info(mngQueryRecordDetailResponse.toString());
                return new ResponseEntity<>(mngQueryRecordDetailResponse, request.getHeaders(), HttpStatus.OK);
            }

            @Override
            public ResponseEntity<MngQueryRecordDetailResponse> handleException(ResponseEntity<MngQueryRecordDetailResponse> result,
                                                                                Exception e) {
                Result resultResult = new Result();
                MngQueryRecordDetailResponse response = new MngQueryRecordDetailResponse();

                resultResult.setResultStatus("F");
                resultResult.setResultCode("FAIL");
                resultResult.setResultMsg(e.getMessage());
                response.setResult(resultResult);

                return new ResponseEntity<MngQueryRecordDetailResponse>(response, request.getHeaders(), 200);
            }
        });

    }

    @Override
    public ResponseEntity<MngUpdateRecordResponse> updateRecord(RequestEntity<MngUpdateRecordRequest> request) {
        return ApiProcessTemplate.execute(request, new ApiProcessFunction<ResponseEntity<MngUpdateRecordResponse>>() {
            @Override
            public ResponseEntity<MngUpdateRecordResponse> execute() throws Exception {

                validateToken(request.getHeaders());
                MngUpdateRecordRequest body = request.getBody();
                Integer conversationId = body.getConversationId();
                ChatRecord chatRecord = chatRecordRepository.findByChatId(conversationId);
                chatRecord.setRemark(body.getRemark());
                chatRecord.setFollowupStatus(body.getFollowupStatus());
                chatRecordRepository.save(chatRecord);

                MngUpdateRecordResponse mngUpdateRecordResponse = new MngUpdateRecordResponse();
                mngUpdateRecordResponse.setConversationId(body.getConversationId());
                mngUpdateRecordResponse.setFollowupStatus(body.getFollowupStatus());
                mngUpdateRecordResponse.setRemark(body.getRemark());
                mngUpdateRecordResponse.setResult(Result.success());

                return new ResponseEntity<MngUpdateRecordResponse>(mngUpdateRecordResponse, request.getHeaders(), 200);
            }

            @Override
            public ResponseEntity<MngUpdateRecordResponse> handleException(ResponseEntity<MngUpdateRecordResponse> result, Exception e) {
                Result resultResult = new Result();
                MngUpdateRecordResponse response = new MngUpdateRecordResponse();

                resultResult.setResultStatus("F");
                resultResult.setResultCode("FAIL");
                resultResult.setResultMsg(e.getMessage());
                response.setResult(resultResult);

                return new ResponseEntity<MngUpdateRecordResponse>(response, request.getHeaders(), 200);
            }
        });

    }

}

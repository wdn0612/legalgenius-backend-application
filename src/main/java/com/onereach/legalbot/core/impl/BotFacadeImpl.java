/*
 * DN
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.impl;

import com.onereach.legalbot.core.async.AsyncService;
import com.onereach.legalbot.core.service.AuthService;
import com.onereach.legalbot.core.template.ApiProcessFunction;
import com.onereach.legalbot.core.template.ApiProcessTemplate;
import com.onereach.legalbot.core.util.ConvertUtil;
import com.onereach.legalbot.core.util.JsonUtil;
import com.onereach.legalbot.core.util.DouyinEncryptionUtil;
import com.onereach.legalbot.facade.BotFacade;
import com.onereach.legalbot.facade.model.ChatRecordVO;
import com.onereach.legalbot.facade.model.Message;
import com.onereach.legalbot.facade.model.Result;
import com.onereach.legalbot.facade.request.ChatRequest;
import com.onereach.legalbot.facade.request.EndChatRequest;
import com.onereach.legalbot.facade.request.DouyinLoginRequest;
import com.onereach.legalbot.facade.request.QueryChatRecordListRequest;
import com.onereach.legalbot.facade.request.ReserveRequest;
import com.onereach.legalbot.facade.request.UpdateProfileRequest;
import com.onereach.legalbot.facade.response.BaseResponse;
import com.onereach.legalbot.facade.response.ChatResponse;
import com.onereach.legalbot.facade.response.EndChatResponse;
import com.onereach.legalbot.facade.response.DouyinLoginResponse;
import com.onereach.legalbot.facade.response.QueryChatRecordListResponse;
import com.onereach.legalbot.facade.response.ReserveResponse;
import com.onereach.legalbot.infrastructure.repository.ChatRecordRepository;
import com.onereach.legalbot.infrastructure.repository.ReservationRepository;
import com.onereach.legalbot.infrastructure.repository.UserRepository;
import com.onereach.legalbot.infrastructure.repository.UserPlatformAccountRepository;
import com.onereach.legalbot.infrastructure.repository.UserSessionRepository;
import com.onereach.legalbot.infrastructure.repository.PartnerRepository;
import com.onereach.legalbot.infrastructure.model.User;
import com.onereach.legalbot.infrastructure.model.UserPlatformAccount;
import com.onereach.legalbot.infrastructure.model.UserSession;
import com.onereach.legalbot.infrastructure.model.ChatRecord;
import com.onereach.legalbot.infrastructure.model.Partner;
import com.onereach.legalbot.infrastructure.model.Reservation;
import com.onereach.legalbot.infrastructure.model.enums.*;
import com.onereach.legalbot.service.ModelService;
import com.onereach.legalbot.service.model.PhoneNumberData;
import com.onereach.legalbot.service.DouyinService;
import com.onereach.legalbot.service.request.Code2SessionRequest;
import com.onereach.legalbot.service.request.CompletionRequest;
import com.onereach.legalbot.service.request.SummaryRequest;
import com.onereach.legalbot.service.response.Code2SessionResponse;
import com.onereach.legalbot.service.response.CompletionResponse;
import com.onereach.legalbot.service.response.SummaryResponse;
import com.fasterxml.jackson.databind.JsonSerializable.Base;
import com.onereach.legalbot.config.AppConfig;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
@Slf4j
@RestController
public class BotFacadeImpl implements BotFacade {

    @Resource
    private ChatRecordRepository chatRecordRepository;

    @Resource
    private ReservationRepository reservationRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserSessionRepository userSessionRepository;

    @Resource
    private UserPlatformAccountRepository userPlatformAccountRepository;

    @Resource
    private PartnerRepository partnerRepository;

    @Resource
    private ModelService modelService;

    @Resource
    private AsyncService asyncService;

    @Resource
    private DouyinService douyinService;

    @Resource
    private AuthService authService;

    @Override
    @PostMapping(path = "/v1/apps/douyin/login")
    public ResponseEntity<DouyinLoginResponse> login(RequestEntity<DouyinLoginRequest> httpRequest) {

        return ApiProcessTemplate.execute(httpRequest, new ApiProcessFunction<ResponseEntity<DouyinLoginResponse>>() {
            @Override
            public ResponseEntity<DouyinLoginResponse> execute() throws Exception {
                DouyinLoginRequest body = httpRequest.getBody();

                if (body == null || body.getCode() == null) {
                    throw new RuntimeException("code is required.");
                }

                // 调用 douyin code2session 接口，获取 openid 和 session_key
                Code2SessionRequest code2SessionRequest = new Code2SessionRequest();
                code2SessionRequest.setCode(body.getCode());
                Code2SessionResponse code2SessionResponse = douyinService.code2Session(code2SessionRequest);
                // 对返回数据异常处理
                if (code2SessionResponse == null || code2SessionResponse.getData() == null
                        || code2SessionResponse.getErrNo() != 0) {
                    throw new RuntimeException("douyin code2session return error. ");
                }

                // 通过 openid 和 platform 查询用户信息
                // 注：抖音 openid 在我们系统中被记录为：platform=dy_open, platformUserId=openid
                String openId = code2SessionResponse.getData().getOpenid();
                Platform platform = Platform.DY_OPEN;
                UserPlatformAccount openPlatformAccount = userPlatformAccountRepository
                        .findByPlatformUserIdAndPlatform(openId, platform);
                if (openPlatformAccount == null) {
                    // 先创建一个空用户
                    User user = userRepository.save(new User());

                    // 创建一个新的 platform account
                    openPlatformAccount = new UserPlatformAccount();
                    openPlatformAccount.setUser(user);
                    openPlatformAccount.setPlatform(platform);
                    openPlatformAccount.setPlatformUserId(openId);
                    userPlatformAccountRepository.save(openPlatformAccount);
                }

                // 同时我们也会记录用户的 unionId
                String unionId = code2SessionResponse.getData().getUnionId();
                Platform unionPlatform = Platform.DY_UNION;
                UserPlatformAccount unionPlatformAccount = userPlatformAccountRepository
                        .findByPlatformUserIdAndPlatform(unionId, unionPlatform);
                if (unionPlatformAccount == null) {
                    // 创建一个新的 platform account
                    unionPlatformAccount = new UserPlatformAccount();
                    unionPlatformAccount.setUser(openPlatformAccount.getUser());
                    unionPlatformAccount.setPlatform(unionPlatform);
                    unionPlatformAccount.setPlatformUserId(unionId);
                    userPlatformAccountRepository.save(unionPlatformAccount);
                }

                // 在数据库 user_session 当中记录用户在前端的登录态信息, userId + scene 作为唯一标识
                Scene scene = body.getScene();
                Integer userId = openPlatformAccount.getUser().getUserId();

                if (userSessionRepository.findByUser_UserIdAndScene(userId, scene) == null) {
                    // 如果数据库中没有该用户的登录态信息，则 create 一个新的登录态信息
                    UserSession userSession = new UserSession();
                    userSession.setUser(openPlatformAccount.getUser());
                    userSession.setScene(scene);
                    userSession.setSceneSessionKey(code2SessionResponse.getData().getSessionKey());
                    userSessionRepository.save(userSession);
                } else {
                    // 如果数据库中有该用户的登录态信息，则更新 sessionKey
                    UserSession userSession = userSessionRepository.findByUser_UserIdAndScene(userId, scene);
                    userSession.setSceneSessionKey(code2SessionResponse.getData().getSessionKey());
                    userSessionRepository.save(userSession);
                }

                // 生成 token，返回给前端，改 token 后续查找该用户的 session key
                User user = openPlatformAccount.getUser();
                String token = authService.generateTokenForUser(user);
                DouyinLoginResponse douyinLoginResponse = new DouyinLoginResponse();
                douyinLoginResponse.setToken(token);
                douyinLoginResponse.setResult(Result.success());

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<DouyinLoginResponse>(douyinLoginResponse, responseHeaders, 200);
            }

            @Override
            public ResponseEntity<DouyinLoginResponse> handleException(ResponseEntity<DouyinLoginResponse> result,
                    Exception e) {
                DouyinLoginResponse douyinLoginResponse = new DouyinLoginResponse();
                Result failResult = Result.fail();
                failResult.setResultMessage(e.getMessage());
                douyinLoginResponse.setResult(failResult);

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<DouyinLoginResponse>(douyinLoginResponse, responseHeaders, 200);
            }
        });
    }

    // @Override
    @PostMapping(path = "/v1/apps/douyin/updateProfile")
    public ResponseEntity<BaseResponse> updateProfile(@Valid RequestEntity<UpdateProfileRequest> httpRequest) {
        return ApiProcessTemplate.execute(httpRequest, new ApiProcessFunction<ResponseEntity<BaseResponse>>() {
            @Override
            public ResponseEntity<BaseResponse> execute() throws Exception {

                UpdateProfileRequest body = httpRequest.getBody();
                if (body == null) {
                    throw new RuntimeException("request body is required.");
                }

                UpdateProfileType type = body.getType();
                Scene scene = body.getScene();
                String jwtToken = httpRequest.getHeaders().getFirst("Authorization");

                if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
                    throw new RuntimeException("Authorization header is required.");
                }
                jwtToken = jwtToken.substring(7); // 剔除 Bearer

                Integer userId = authService.getUserIdFromToken(jwtToken);

                // 根据 userId 查询用户信息
                UserSession userSession = userSessionRepository.findByUser_UserIdAndScene(userId, scene);
                if (userSession == null) {
                    throw new RuntimeException("user session not found.");
                }

                if (type == UpdateProfileType.USERPROFILE) {
                    // 根据用户 openId, 更新用户 platform account 信息
                    User user = userSession.getUser();
                    UserPlatformAccount userPlatformAccount = userPlatformAccountRepository
                            .findByUser_UserIdAndPlatform(
                                    user.getUserId(), Platform.DY_OPEN);
                    String platformUserName = body.getUserName();
                    String platformGender = body.getGender();
                    String platformCity = body.getCity();
                    String platformProvince = body.getProvince();
                    String platformCountry = body.getCountry();
                    String platformAvatarUrl = body.getAvatarUrl();

                    userPlatformAccount.setPlatformUserName(platformUserName);
                    userPlatformAccount.setPlatformGender(platformGender);
                    userPlatformAccount.setPlatformCity(platformCity);
                    userPlatformAccount.setPlatformProvince(platformProvince);
                    userPlatformAccount.setPlatformCountry(platformCountry);
                    userPlatformAccount.setPlatformAvatarUrl(platformAvatarUrl);
                    userPlatformAccountRepository.save(userPlatformAccount);

                } else if (type == UpdateProfileType.PHONENUMBER) {
                    String sessionKey = userSession.getSceneSessionKey();
                    String iv = body.getIv();
                    String encryptedData = body.getEncryptedData();

                    String decryptedData = DouyinEncryptionUtil.decrypt(encryptedData, sessionKey, iv);
                    log.info("decryptedData: {}", decryptedData);

                    PhoneNumberData phoneNumberData = JsonUtil.fromJson(decryptedData, PhoneNumberData.class);
                    String phoneNumber = phoneNumberData.getPhoneNumber();

                    // 更新用户手机号
                    User user = userSession.getUser();
                    user.setPhoneNumber(phoneNumber);
                    userRepository.save(user);
                } else {
                    throw new RuntimeException("type not supported.");
                }

                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(Result.success());

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<BaseResponse>(baseResponse, responseHeaders, 200);

            }

            @Override
            public ResponseEntity<BaseResponse> handleException(ResponseEntity<BaseResponse> result, Exception e) {

                BaseResponse baseResponse = new BaseResponse();
                Result failResult = Result.fail();
                failResult.setResultMessage(e.getMessage());
                baseResponse.setResult(failResult);

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<BaseResponse>(baseResponse, responseHeaders, 200);
            }
        });
    }

    @Override
    @PostMapping(path = "/v1/chat")
    public ResponseEntity<ChatResponse> chat(RequestEntity<ChatRequest> httpRequest) {

        return ApiProcessTemplate.execute(httpRequest,
                new ApiProcessFunction<ResponseEntity<ChatResponse>>() {
                    @Override
                    public ResponseEntity<ChatResponse> execute() throws Exception {

                        ChatRequest body = httpRequest.getBody();
                        List<Message> conversation = body.getConversation();
                        ChatRecord chatRecord;
                        String title = null;

                        // 从 JWT token 中获取 userId
                        String jwtToken = httpRequest.getHeaders().getFirst("Authorization");
                        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
                            throw new RuntimeException("Authorization header is required.");
                        }
                        jwtToken = jwtToken.substring(7); // 剔除 Bearer
                        Integer userId = authService.getUserIdFromToken(jwtToken);

                        if (body.getConversationId() != null) {
                            chatRecord = chatRecordRepository.findByChatId(
                                    body.getConversationId());
                            chatRecord.setMessages(JsonUtil.listToJsonArrayStr(conversation));
                            chatRecordRepository.save(chatRecord);
                        } else {
                            // 初始化chatRecord
                            ChatRecord newChatRecord = new ChatRecord();
                            newChatRecord.setCreatedAt(LocalDateTime.now());
                            newChatRecord.setModifiedAt(LocalDateTime.now());
                            User user = userRepository.findByUserId(userId);
                            String partnerName = body.getPartner();
                            Partner partner = partnerRepository.findByPartnerName(partnerName);
                            newChatRecord.setUser(user);
                            newChatRecord.setPartner(partner);
                            newChatRecord.setScene(body.getScene());
                            newChatRecord.setMessages(JsonUtil.listToJsonArrayStr(conversation));
                            newChatRecord.setReservationIntent(false);
                            chatRecord = chatRecordRepository.save(newChatRecord);
                        }

                        if (conversation.size() == 3) {
                            SummaryRequest summaryRequest = new SummaryRequest();
                            summaryRequest.setSummaryType("TITLE");
                            summaryRequest.setMessages(conversation);
                            SummaryResponse summaryResponse = modelService.summarize(
                                    summaryRequest);
                            title = summaryResponse.getSummary();
                        }

                        CompletionRequest completionRequest = new CompletionRequest();
                        completionRequest.setMessages(conversation);
                        CompletionResponse completionResponse = modelService.complete(
                                completionRequest);
                        String completion = completionResponse.getSystemCompletion();
                        conversation.add(new Message(Role.ASSISTANT, completion));
                        chatRecord.setMessages(JsonUtil.listToJsonArrayStr(conversation));
                        chatRecord.setReservationIntent(completionResponse.isReservationIntent());

                        chatRecordRepository.save(chatRecord);

                        // 组装返回结果
                        ChatResponse chatResponse = new ChatResponse();
                        chatResponse.setConversationId(chatRecord.getChatId());
                        chatResponse.setChatResponse(completion);
                        chatResponse.setReservationIntent(false);
                        chatResponse.setTitle(title);
                        chatResponse.setResult(Result.success());

                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

                        return new ResponseEntity<ChatResponse>(chatResponse, responseHeaders, 200);
                    }

                    @Override
                    public ResponseEntity<ChatResponse> handleException(
                            ResponseEntity<ChatResponse> result, Exception e) {
                        ChatResponse chatResponse = new ChatResponse();
                        Result resultResult = new Result();
                        resultResult.setResultStatus("F");
                        resultResult.setResultCode("FAIL");

                        // TODO: backend errors exposed to frontend, seems dangerous.
                        resultResult.setResultMessage(e.getMessage());
                        chatResponse.setResult(resultResult);

                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

                        return new ResponseEntity<ChatResponse>(chatResponse, responseHeaders, 200);
                    }
                });
    }

    @Override
    @PostMapping(path = "/v1/reserve")
    public ResponseEntity<ReserveResponse> reserve(RequestEntity<ReserveRequest> httpRequest) {
        return ApiProcessTemplate.execute(httpRequest,
                new ApiProcessFunction<ResponseEntity<ReserveResponse>>() {

                    @Override
                    public ResponseEntity<ReserveResponse> execute() throws Exception {

                        ReserveRequest reserveRequest = httpRequest.getBody();
                        Reservation reservation = new Reservation();
                        reservation.getUser().setUserId(reserveRequest.getUserId());
                        reservation.getChatRecord().setChatId(reserveRequest.getConversationId());
                        reservation.setReservationDate(LocalDateTime.now());
                        reservation.setCreatedAt(LocalDateTime.now());

                        Reservation savedReservation = reservationRepository.save(reservation);
                        Integer reservationId = savedReservation.getReservationId();

                        ChatRecord chatRecord = chatRecordRepository.findByChatId(
                                reserveRequest.getConversationId());
                        chatRecord.setReservationId(reservationId);
                        chatRecordRepository.save(chatRecord);
                        ReserveResponse reserveResponse = new ReserveResponse();
                        reserveResponse.setResult(Result.success());
                        reserveResponse.setReservationId(reservationId);

                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

                        return new ResponseEntity<ReserveResponse>(reserveResponse, responseHeaders,
                                200);
                    }

                    @Override
                    public ResponseEntity<ReserveResponse> handleException(
                            ResponseEntity<ReserveResponse> result, Exception e) {
                        Result resultResult = new Result();
                        ReserveResponse response = new ReserveResponse();

                        resultResult.setResultStatus("F");
                        resultResult.setResultCode("FAIL");
                        resultResult.setResultMessage(e.getMessage());
                        response.setResult(resultResult);

                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
                        return new ResponseEntity<ReserveResponse>(response,
                                responseHeaders, 200);
                    }
                });
    }

    @Override
    @PostMapping(path = "/v1/endChat")
    public ResponseEntity<EndChatResponse> endChat(RequestEntity<EndChatRequest> endChatRequest) {
        return ApiProcessTemplate.execute(endChatRequest,
                new ApiProcessFunction<ResponseEntity<EndChatResponse>>() {

                    @Override
                    public ResponseEntity<EndChatResponse> execute() throws Exception {
                        asyncService.generatePostChatInfo(
                                endChatRequest.getBody().getConversationId());

                        EndChatResponse endChatResponse = new EndChatResponse();
                        endChatResponse.setConversationId(
                                endChatRequest.getBody().getConversationId());
                        endChatResponse.setResult(Result.success());
                        return new ResponseEntity<EndChatResponse>(endChatResponse,
                                endChatRequest.getHeaders(), 200);
                    }

                    @Override
                    public ResponseEntity<EndChatResponse> handleException(
                            ResponseEntity<EndChatResponse> result, Exception e) {
                        Result resultResult = new Result();
                        EndChatResponse response = new EndChatResponse();

                        resultResult.setResultStatus("F");
                        resultResult.setResultCode("FAIL");
                        resultResult.setResultMessage(e.getMessage());
                        response.setResult(resultResult);

                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

                        return new ResponseEntity<EndChatResponse>(response, responseHeaders, 200);
                    }
                });
    }

    @Override
    @PostMapping("/v1/queryChatRecordList")
    public ResponseEntity<QueryChatRecordListResponse> queryChatRecordList(
            RequestEntity<QueryChatRecordListRequest> request) {
        return ApiProcessTemplate.execute(request,
                new ApiProcessFunction<ResponseEntity<QueryChatRecordListResponse>>() {
                    @Override
                    public ResponseEntity<QueryChatRecordListResponse> execute() throws Exception {
                        List<ChatRecord> chatRecords = chatRecordRepository.findByUser_UserIdOrderByCreatedAtDesc(
                                request.getBody().getUserId());
                        List<ChatRecordVO> chatRecordVOList = chatRecords.stream()
                                .map(ConvertUtil::convertToChatRecordVO)
                                .collect(Collectors.toList());
                        QueryChatRecordListResponse queryChatRecordListResponse = new QueryChatRecordListResponse();
                        queryChatRecordListResponse.setTotalRecords(chatRecordVOList.size());
                        queryChatRecordListResponse.setRecords(chatRecordVOList);
                        queryChatRecordListResponse.setResult(Result.success());
                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

                        return new ResponseEntity<QueryChatRecordListResponse>(
                                queryChatRecordListResponse, responseHeaders, 200);
                    }

                    @Override
                    public ResponseEntity<QueryChatRecordListResponse> handleException(
                            ResponseEntity<QueryChatRecordListResponse> result, Exception e) {
                        Result resultResult = new Result();
                        QueryChatRecordListResponse response = new QueryChatRecordListResponse();

                        resultResult.setResultStatus("F");
                        resultResult.setResultCode("FAIL");
                        resultResult.setResultMessage(e.getMessage());
                        response.setResult(resultResult);

                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

                        return new ResponseEntity<QueryChatRecordListResponse>(response,
                                responseHeaders, 200);
                    }
                });
    }

    ;
}
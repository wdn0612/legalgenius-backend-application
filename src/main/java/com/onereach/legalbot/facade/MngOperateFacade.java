/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade;

import com.onereach.legalbot.facade.request.LoginRequest;
import com.onereach.legalbot.facade.request.MngQueryRecordDetailRequest;
import com.onereach.legalbot.facade.request.MngQueryRecordListRequest;
import com.onereach.legalbot.facade.request.MngUpdateRecordRequest;
import com.onereach.legalbot.facade.response.MngQueryRecordDetailResponse;
import com.onereach.legalbot.facade.response.MngQueryRecordListResponse;
import com.onereach.legalbot.facade.response.MngUpdateRecordResponse;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * 对外暴露的运营操作接口
 *
 * @author wangdaini
 * @version MngOperateFacade.java, v 0.1 2024年02月15日 11:03 pm wangdaini
 */
public interface MngOperateFacade {

    ResponseEntity<String> login (@RequestBody LoginRequest request);

    ResponseEntity<MngQueryRecordListResponse> queryRecordList(RequestEntity<MngQueryRecordListRequest> request);


    ResponseEntity<MngQueryRecordDetailResponse> queryRecordDetail(RequestEntity<MngQueryRecordDetailRequest> request);

    ResponseEntity<MngUpdateRecordResponse> updateRecord(RequestEntity<MngUpdateRecordRequest> request);
}
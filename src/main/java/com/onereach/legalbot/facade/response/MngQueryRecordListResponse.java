/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.response;

import com.onereach.legalbot.facade.model.ChatRecordVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author wangdaini
 * @version MngQueryRecordListResponse.java, v 0.1 2024年08月27日 9:03 pm wangdaini
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MngQueryRecordListResponse extends BaseResponse{

    private Integer totalRecords;
    private Integer totalPages;
    private Integer            currentPage;
    private List<ChatRecordVO> recordList;

}
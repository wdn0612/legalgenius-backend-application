/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wangdaini
 * @version MngQueryRecordList.java, v 0.1 2024年08月27日 9:01 pm wangdaini
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MngQueryRecordListRequest extends BaseRequest {

    private static final long serialVersionUID = -1230405465658440005L;

    private Integer currentPage;
    private Integer pageSize;
    private Integer partnerId;

    /**
     * 目前只有CreatedTime, ModifedTime, Priority
     */
    private String sortBy;

    /**
     * 赠序/降序 ASC/DESC
     */
    private String order;
}
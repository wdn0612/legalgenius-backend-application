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

/**
 * @author wangdaini
 * @version MngQueryRecordDetailResponse.java, v 0.1 2024年08月27日 9:39 pm wangdaini
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MngQueryRecordDetailResponse extends BaseResponse{

    private static final long serialVersionUID = 8995294396960279634L;
    private ChatRecordVO record;

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("MngQueryRecordDetailResponse [record=");
        builder.append(record);
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }
}
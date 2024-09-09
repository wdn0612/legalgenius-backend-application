/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.util;

import com.onereach.legalbot.facade.model.ChatRecordVO;
import com.onereach.legalbot.facade.model.Message;
import com.onereach.legalbot.infrastructure.model.ChatRecord;

/**
 * @author wangdaini
 * @version ConvertUtil.java, v 0.1 2024年08月30日 9:24 pm wangdaini
 */
public class ConvertUtil {

    public static ChatRecordVO convertToChatRecordVO(ChatRecord chatRecord) {
        if (chatRecord == null) {
            return null;
        }
        ChatRecordVO chatRecordVO = new ChatRecordVO();
        chatRecordVO.setCreatedTime(DateUtil.localDateTimeToUtcString(chatRecord.getCreatedAt()));
        chatRecordVO.setModifiedTime(DateUtil.localDateTimeToUtcString(chatRecord.getModifiedAt()));
        chatRecordVO.setConversationId(chatRecord.getChatId());
        chatRecordVO.setUserId(chatRecord.getUser().getUserId());
        chatRecordVO.setPartnerId(chatRecord.getPartner().getPartnerId());
        chatRecordVO.setScene(chatRecord.getScene().name());
        chatRecordVO.setMessage(JsonUtil.jsonArrayToObjectList(chatRecord.getMessage(), Message.class));
        chatRecordVO.setReserved(chatRecord.getReservationId() != null);
        chatRecordVO.setCategory(chatRecord.getCategory());
        chatRecordVO.setSummary(chatRecord.getSummary());
        chatRecordVO.setPriority(chatRecord.getPriority());
        chatRecordVO.setFollowupStatus(chatRecord.getFollowupStatus().name());
        chatRecordVO.setRemark(chatRecord.getRemark());
        return chatRecordVO;
    }
}
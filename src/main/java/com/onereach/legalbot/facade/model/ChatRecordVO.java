/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangdaini
 * @version ChatRecordVO.java, v 0.1 2024年08月27日 9:06 pm wangdaini
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRecordVO implements Serializable {

    private static final long serialVersionUID = -4474227876399948243L;
    private String createdTime;
    private String modifiedTime;
    private Integer conversationId;
    private Integer userId;
    private Integer partnerId;
    private String platform;
    private List<Message> message;
    private boolean reserved;
    private String category;
    private String summary;
    private Integer priority;
    private String followupStatus;
    private String remark;

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("ChatRecordVO [createdTime='");
        builder.append(createdTime).append("'");
        builder.append(", modifiedTime='");
        builder.append(modifiedTime).append("'");
        builder.append(", conversationId=");
        builder.append(conversationId);
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", partnerId=");
        builder.append(partnerId);
        builder.append(", platform='");
        builder.append(platform).append("'");
        builder.append(", message=");
        builder.append(message);
        builder.append(", reserved=");
        builder.append(reserved);
        builder.append(", category='");
        builder.append(category).append("'");
        builder.append(", summary='");
        builder.append(summary).append("'");
        builder.append(", priority=");
        builder.append(priority);
        builder.append(", followupStatus='");
        builder.append(followupStatus).append("'");
        builder.append(", remark='");
        builder.append(remark).append("'");
        builder.append("]");
        return builder.toString();
    }
}
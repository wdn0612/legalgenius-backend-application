/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.model;

import com.lark.oapi.service.bitable.v1.model.AppTableFieldProperty;
import lombok.Data;

/**
 * 字段信息响应
 *
 * @author wangdaini
 * @version FieldInfoResponse.java, v 0.1 2024年02月20日 12:14 am wangdaini
 */
@Data
public class FieldInfoResponse {

    private String                fieldId;
    private String                fieldName;
    private AppTableFieldProperty property;
    private Integer               type;
    private String                uiType;

}
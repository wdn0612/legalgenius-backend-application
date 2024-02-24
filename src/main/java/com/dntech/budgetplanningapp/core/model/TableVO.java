/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.model;

import lombok.Data;

/**
 * 表格信息
 *
 * @author wangdaini
 * @version Table.java, v 0.1 2024年02月16日 12:00 am wangdaini
 */
@Data
public class TableVO {

    private String tableId;
    private int    revision;
    private String name;
}
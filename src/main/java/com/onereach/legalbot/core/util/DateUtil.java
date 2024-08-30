/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.core.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author wangdaini
 * @version DateUtil.java, v 0.1 2024年08月27日 9:08 pm wangdaini
 */
public class DateUtil {

    public static String localDateTimeToUtcString (LocalDateTime localDateTime) {

        return localDateTime.atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_INSTANT);

    }

}
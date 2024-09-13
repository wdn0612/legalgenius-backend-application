/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * @author wangdaini
 * @version SummaryResponse.java, v 0.1 2024年08月26日 6:35 pm wangdaini
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SummaryResponse extends BaseResponse {

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("title")
    private String title;

    public boolean hasSummary() {
        return summary != null && !summary.isEmpty();
    }

    public boolean hasTitle() {
        return title != null && !title.isEmpty();
    }
}
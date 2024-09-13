package com.onereach.legalbot.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ModelResult implements Serializable {
    private static final long serialVersionUID = 7973371907432211564L;

    @JsonProperty("result_status")
    private String resultStatus;

    @JsonProperty("result_code")
    private String resultCode;

    @JsonProperty("result_message")
    private String resultMessage;

}
/*
 * DN 
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.onereach.legalbot.facade.request;

import com.onereach.legalbot.infrastructure.model.enums.UpdateProfileType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest extends BaseRequest {

    private String encryptedData;

    private String iv;

    // 目前有两种 type: PROFILE, PHONENUMBER
    private UpdateProfileType type;

    private String userName;

    private String gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;
}
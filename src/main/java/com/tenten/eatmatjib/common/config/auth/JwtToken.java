package com.tenten.eatmatjib.common.config.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}

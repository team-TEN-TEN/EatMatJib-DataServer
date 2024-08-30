package com.tenten.eatmatjib.member.controller.response;

import com.tenten.eatmatjib.common.config.auth.JwtToken;
import com.tenten.eatmatjib.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 로그인 응답 객체")
@Getter
@Builder
public class LoginMemberRes {

    @Schema(description = "사용자 계정", example = "tenten")
    private String account;

    @Schema(description = "액세스 토큰")
    private String accessToken;

    @Schema(description = "리프레시 토큰")
    private String refreshToken;

    public static LoginMemberRes of(Member member, JwtToken token) {
        return LoginMemberRes.builder()
                .account(member.getAccount())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}

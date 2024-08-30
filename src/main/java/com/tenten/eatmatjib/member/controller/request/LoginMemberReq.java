package com.tenten.eatmatjib.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 로그인 요청 객체")
@Getter
@Builder
public class LoginMemberReq {

    @Schema(description = "계정", example = "tenten")
    @NotBlank(message = "계정은 필수 입력입니다.")
    private String account;

    @Schema(description = "비밀번호", example = "password12!")
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    private String password;
}

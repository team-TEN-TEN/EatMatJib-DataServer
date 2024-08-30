package com.tenten.eatmatjib.member.controller.request;

import com.tenten.eatmatjib.common.util.RegexPatternUtil;
import com.tenten.eatmatjib.member.domain.Member;
import com.tenten.eatmatjib.member.domain.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 회원가입 요청 객체")
@Getter
@Builder
public class RegisterMemberReq {

    @Schema(description = "계정", example = "tenten")
    @NotBlank(message = "계정은 필수 입력입니다.")
    @Size(min = 1, max = 50, message = "계정은 1~50자만 가능합니다.")
    private String account;

    @Schema(description = "이메일", example = "tenten@gmail.com")
    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "올바른 이메일 형식을 입력해 주세요.")
    @Size(min = 1, max = 100, message = "이메일은 1~100자만 가능합니다.")
    private String email;

    @Schema(description = "비밀번호", example = "password12!")
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Pattern(regexp = RegexPatternUtil.PASSWORD, message = "비밀번호는 1) 최소 10자 이상, 2) 숫자/문자/특수문자(!@#$%^&*) 중 2가지 이상 포함, 3) 3회 이상 연속되는 문자를 사용할 수 없습니다.")
    private String password;

    public Member toMember(String encryptedPassword) {
        return Member.builder()
                .account(account)
                .email(email)
                .password(encryptedPassword)
                .joinedAt(LocalDateTime.now())
                .build();
    }
}

package com.tenten.eatmatjib.member.controller.response;

import com.tenten.eatmatjib.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 회원가입 응답 객체")
@Getter
@Builder
public class RegisterMemberRes {

    @Schema(description = "사용자 id", example = "uuid")
    private String memberId;

    public static RegisterMemberRes of(Member member) {
        return RegisterMemberRes.builder()
                .memberId(member.getId())
                .build();
    }
}

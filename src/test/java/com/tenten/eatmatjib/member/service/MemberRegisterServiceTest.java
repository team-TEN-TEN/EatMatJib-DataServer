package com.tenten.eatmatjib.member.service;

import static com.tenten.eatmatjib.common.exception.ErrorCode.ACCOUNT_CONFLICT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.tenten.eatmatjib.common.exception.BusinessException;
import com.tenten.eatmatjib.member.controller.request.RegisterMemberReq;
import com.tenten.eatmatjib.member.controller.response.RegisterMemberRes;
import com.tenten.eatmatjib.member.domain.Member;
import com.tenten.eatmatjib.member.repository.MemberRepository;
import com.tenten.eatmatjib.verification.domain.VerificationCode;
import com.tenten.eatmatjib.verification.repository.VerificationCodeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberRegisterServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private VerificationCodeRepository verificationCodeRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberRegisterService memberRegisterService;

    @DisplayName("사용자가 회원가입을 하면 사용자 회원가입 응답 객체를 반환한다.")
    @Test
    void memberRegisterReturnRegisterMemberRes() {
        // given
        RegisterMemberReq request = getRegisterMemberReq();
        Member member = request.toMember(passwordEncoder.encode(request.getPassword()));
        VerificationCode verificationCode = VerificationCode.create(member);

        when(memberRepository.existsByAccount(any())).thenReturn(false);
        when(memberRepository.save(any())).thenReturn(member);
        when(verificationCodeRepository.save(any())).thenReturn(verificationCode);

        // when
        RegisterMemberRes result = memberRegisterService.execute(request);

        // then
        assertThat(result.getMemberId()).isEqualTo(member.getId());
    }

    @DisplayName("사용자가 이미 사용중인 계정으로 회원가입을 하면 예외가 발생한다.")
    @Test
    void memberAccountConflictRegisterThrowsException() {
        // given
        RegisterMemberReq request = getRegisterMemberReq();

        when(memberRepository.existsByAccount(any())).thenReturn(true);

        // when
        BusinessException exception = assertThrows(BusinessException.class,
            () -> memberRegisterService.execute(request)
        );

        // then
        assertThat(exception.getErrorCode()).isEqualTo(ACCOUNT_CONFLICT);
    }

    private RegisterMemberReq getRegisterMemberReq() {
        return RegisterMemberReq.builder()
            .account("tenten")
            .email("tenten@gmail.com")
            .password("password12!")
            .build();
    }
}
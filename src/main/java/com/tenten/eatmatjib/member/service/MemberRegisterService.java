package com.tenten.eatmatjib.member.service;

import static com.tenten.eatmatjib.common.exception.ErrorCode.ACCOUNT_CONFLICT;

import com.tenten.eatmatjib.common.exception.BusinessException;
import com.tenten.eatmatjib.member.controller.request.RegisterMemberReq;
import com.tenten.eatmatjib.member.controller.response.RegisterMemberRes;
import com.tenten.eatmatjib.member.domain.Member;
import com.tenten.eatmatjib.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberRegisterService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterMemberRes execute(RegisterMemberReq request) {
        verifyAccount(request.getAccount());

        Member member = request.toMember(passwordEncoder.encode(request.getPassword()));
        memberRepository.save(member);

        return RegisterMemberRes.of(member);
    }

    private void verifyAccount(String account) {
        if (memberRepository.existsByAccount(account)) {
            throw new BusinessException(ACCOUNT_CONFLICT);
        }
    }
}

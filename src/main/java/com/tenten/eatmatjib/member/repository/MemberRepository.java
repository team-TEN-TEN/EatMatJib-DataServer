package com.tenten.eatmatjib.member.repository;

import com.tenten.eatmatjib.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByAccount(String account);

    Optional<Member> findByAccount(String account);
}

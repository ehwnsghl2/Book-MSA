package com.brandjunhoe.user.member.infra;

import com.brandjunhoe.user.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}

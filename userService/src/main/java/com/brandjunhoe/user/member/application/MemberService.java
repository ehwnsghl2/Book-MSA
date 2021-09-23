package com.brandjunhoe.user.member.application;

import com.brandjunhoe.user.member.domain.Member;
import com.brandjunhoe.user.member.infra.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> list() {
        return memberRepository.findAll();
    }

}

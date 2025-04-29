package com.example.memberservice.application;

import org.springframework.stereotype.Service;

import com.example.memberservice.application.dto.MemberRegisterRequest;
import com.example.memberservice.domain.Member;
import com.example.memberservice.domain.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long register(MemberRegisterRequest request){

        if(memberRepository.findByLoginId(request.loginId()).isPresent()){
            throw new IllegalArgumentException("이미 사용 중인 로그인 ID입니다.");
        }

        Member member = Member.of(
            request.loginId()
            ,request.password()
            ,request.organizationId()
            ,request.name()
            ,request.role());
        Member newMember = memberRepository.save(member);
        return newMember.getId();
    }

    @Transactional
    public void changePassword(String loginId, String currentPw, String newPw){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        member.changePassword(currentPw, newPw);
        memberRepository.save(member);
    }
}

package com.example.memberservice.application;

import org.springframework.stereotype.Service;

import com.example.memberservice.application.dto.LoginRequest;
import com.example.memberservice.application.dto.LoginResponse;
import com.example.memberservice.application.dto.MemberRegisterRequest;
import com.example.memberservice.config.jwt.JwtProvider;
import com.example.memberservice.domain.Member;
import com.example.memberservice.domain.MemberRepository;
import com.example.memberservice.domain.Role;
import com.example.memberservice.presentation.exception.ErrorCode;
import com.example.memberservice.presentation.exception.MemberException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    @Transactional
    public Long register(MemberRegisterRequest request){

        if(memberRepository.findByLoginId(request.loginId()).isPresent()){
            throw new MemberException(ErrorCode.MEMBER_LOGIN_ID_ALREADY_EXIST);
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
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        member.changePassword(currentPw, newPw);
        memberRepository.save(member);
    }

    @Transactional
    public void changeRole(Long memberId, Long targetId, Role role){
        //1.memberId 권한체크
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        if(member.getRole() != Role.ADMIN){
            throw new MemberException(ErrorCode.MEMBER_ROLE_CHANGE_FORBIDDEN);
        }
        //2.targetId 소속 확인
        Member targetMember = memberRepository.findById(targetId).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        if(!member.getOrganizationId().equals(targetMember.getOrganizationId())){
            throw new MemberException(ErrorCode.MEMBER_ORGANIZATION_MISMATCH);
        }
        //3.변경
        targetMember.changeRole(role);
        memberRepository.save(targetMember);//targetMember 영속상태, 생략가능
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByLoginId(request.loginId())
            .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.isPasswordMatch(request.password())) {
            throw new MemberException(ErrorCode.MEMBER_PASSWORD_MISMATCH);
        }

        String token = jwtProvider.generateAccessToken(member.getId(), member.getName());
        return new LoginResponse(token);
    }
}

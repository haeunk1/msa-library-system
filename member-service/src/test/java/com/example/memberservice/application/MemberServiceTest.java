package com.example.memberservice.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.memberservice.application.dto.MemberRegisterRequest;
import com.example.memberservice.domain.Member;
import com.example.memberservice.domain.MemberRepository;
import com.example.memberservice.domain.Role;
import com.example.memberservice.presentation.exception.ErrorCode;
import com.example.memberservice.presentation.exception.MemberException;

import org.junit.jupiter.api.Nested;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    // 테스트클래스 안에 선언된 @Mock, @InjectMocks 초기화
    // --> @ExtendWith(MockitoExtension.class)
    // public MemberServiceTest() {
    //     MockitoAnnotations.openMocks(this);
    // }
    private Member getMember(){
        return Member.of("loginId","password",1L,"홍길동",Role.USER);
    }

    private Member getAdminMember(){
        return Member.of("adminLoginId","password",1L,"관리자",Role.ADMIN);
    }

    private MemberRegisterRequest getMemberRequest(){
        return new MemberRegisterRequest("loginId","password",1L,"홍길동",Role.USER);
    }

    @Nested
    class 회원가입{
        @Test
        void 회원가입_성공(){
            // given
            MemberRegisterRequest request = getMemberRequest();
            when(memberRepository.findByLoginId(request.loginId())).thenReturn(Optional.empty());
            Member savedMember = getMember();
            when(memberRepository.save(any(Member.class))).thenReturn(savedMember);
            // when
            Long memberId = memberService.register(request);

            // then
            assertEquals(savedMember.getId(), memberId);
            verify(memberRepository).save(any(Member.class));
        }

        @Test
        void 회원가입_실패_로그인ID중복() {
            // given
            MemberRegisterRequest request = getMemberRequest();
            when(memberRepository.findByLoginId(request.loginId())).thenReturn(Optional.of(getMember())); // 중복 있음

            // when & then
            MemberException exception = assertThrows(MemberException.class, () -> {
                memberService.register(request);
            });

            assertEquals(ErrorCode.MEMBER_LOGIN_ID_ALREADY_EXIST.getMessage(), exception.getMessage());
        }
    }
    @Nested
    class 비민번호_변경{
        @Test
        void 비밀번호_변경_성공() {
            // given
            Member member = getMember();
            when(memberRepository.findByLoginId("loginId")).thenReturn(Optional.of(member));
    
            // when
            memberService.changePassword("loginId", "password", "newPassword");
    
            // then
            assertTrue(member.getPassword().isMatch("newPassword"));
            verify(memberRepository).save(member); 
        }
    
        @Test
        void 비밀번호_변경_실패_불일치(){
            //given
            Member member = getMember();
            when(memberRepository.findByLoginId("loginId")).thenReturn(Optional.of(member));
    
            //when&then
            MemberException exception = assertThrows(MemberException.class, () -> {
                memberService.changePassword("loginId", "wrongPassword", "newPassword");
            });
            assertEquals(ErrorCode.MEMBER_NOT_FOUND.getMessage(), exception.getMessage());
        }
    }

    @Nested
    class 권한_변경{
        @Test
        void 권한_변경_성공(){
            //given
            Member adminMember = getAdminMember();
            Member targetMember = Member.of("targetLoginId","password",1L,"홍길동2",Role.USER);
            when(memberRepository.findById(adminMember.getId())).thenReturn(Optional.of(adminMember));
            when(memberRepository.findById(targetMember.getId())).thenReturn(Optional.of(targetMember));
            //when
            memberService.changeRole(adminMember.getId(), targetMember.getId(), Role.ADMIN);
            //then
            assertEquals(targetMember.getRole(),Role.ADMIN);
        }

        @Test
        void 권한_변경_실패_권한없음(){
            //given
            Member member = getMember();
            Member targetMember = Member.of("targetLoginId","password",1L,"홍길동2",Role.USER);
            when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
            when(memberRepository.findById(targetMember.getId())).thenReturn(Optional.of(targetMember));
            //when&then
            MemberException exception = assertThrows(MemberException.class, () -> {
                memberService.changeRole(member.getId(), targetMember.getId(), Role.ADMIN);
            });
            
            assertEquals(ErrorCode.MEMBER_ROLE_CHANGE_FORBIDDEN.getMessage(), exception.getMessage());
        }
        @Test
        void 권한_변경_실패_소속이다름(){
            //given
            Member adminMember = getAdminMember();
            ReflectionTestUtils.setField(adminMember, "id", 1L);//ID 강제 지정
            Member targetMember = Member.of("targetLoginId","password",2L,"홍길동2",Role.USER);
            ReflectionTestUtils.setField(targetMember, "id", 2L);//ID 강제 지정

            when(memberRepository.findById(adminMember.getId())).thenReturn(Optional.of(adminMember));
            when(memberRepository.findById(targetMember.getId())).thenReturn(Optional.of(targetMember));
            //when&then
            MemberException exception = assertThrows(MemberException.class, () -> {
                memberService.changeRole(adminMember.getId(), targetMember.getId(), Role.ADMIN);
            });

            assertEquals(ErrorCode.MEMBER_ORGANIZATION_MISMATCH.getMessage(), exception.getMessage());
        }
    }
    
}

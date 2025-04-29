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

import com.example.memberservice.application.dto.MemberRegisterRequest;
import com.example.memberservice.domain.Member;
import com.example.memberservice.domain.MemberRepository;
import com.example.memberservice.domain.Role;
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
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                memberService.register(request);
            });

            assertEquals("이미 사용 중인 로그인 ID입니다.", exception.getMessage());
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
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                memberService.changePassword("loginId", "wrongPassword", "newPassword");
            });
            assertEquals("현재 비밀번호가 일치하지 않습니다.", exception.getMessage());
        }
    }
    
}

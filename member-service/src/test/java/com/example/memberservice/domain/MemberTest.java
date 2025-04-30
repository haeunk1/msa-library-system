package com.example.memberservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.memberservice.presentation.exception.ErrorCode;
import com.example.memberservice.presentation.exception.MemberException;

import org.junit.jupiter.api.Nested;

public class MemberTest {
    private Member getMember(){
        return Member.of("loginId","password",1L,"홍길동",Role.USER);
    }

    @Nested
    class 비밀번호_변경{
        @Test
        void 비밀번호_변경_성공() {
            //given
            Member member = getMember();
            //when
            member.changePassword("password", "newPassword");
            //then
            assertTrue(member.getPassword().isMatch("newPassword"));
            
        }
    
        @Test
        void 비밀번호_변경_실패_불일치(){
            //given
            Member member = getMember();
            // when & then
            MemberException exception = assertThrows(MemberException.class, () -> {
                member.changePassword("wrongPassword", "newPassword");
            });
            assertEquals(ErrorCode.MEMBER_PASSWORD_MISMATCH.getMessage(), exception.getMessage());
            
        }

        @Test
        void 비밀번호_변경_실패_비밀번호형식틀림(){
            //given
            Member member = getMember();
            // when & then
            MemberException exception = assertThrows(MemberException.class, () -> {
                member.changePassword("password", "123");
            });
            assertEquals(ErrorCode.MEMBER_PASSWORD_TOO_SHORT.getMessage(), exception.getMessage());
            
        }
    }

    @Nested
    class 권한_변경{
        @Test
        void 권한_변경_성공(){
            //given
            Member member = getMember();
            //when
            member.changeRole(Role.ADMIN);
            //then
            assertEquals(member.getRole(),Role.ADMIN);
        }
    }
    
}

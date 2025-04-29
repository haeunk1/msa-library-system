package com.example.memberservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

public class MemberTest {
    private Member getMember(){
        return Member.of("loginId","password",1L,"홍길동",Role.USER);
    }

    @Nested
    class 비밀번호_변경{
        @Test
        void 성공() {
            //given
            Member member = getMember();
            //when
            member.changePassword("password", "newPassword");
            //then
            assertTrue(member.getPassword().isMatch("newPassword"));
            
        }
    
        @Test
        void 실패_불일치(){
            //given
            Member member = getMember();
            // when & then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                member.changePassword("wrongPassword", "newPassword");
            });
            assertEquals("현재 비밀번호가 일치하지 않습니다.", exception.getMessage());
            
        }
    }
    
}

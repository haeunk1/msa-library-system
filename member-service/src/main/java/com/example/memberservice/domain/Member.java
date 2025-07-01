package com.example.memberservice.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.memberservice.domain.vo.LoginId;
import com.example.memberservice.domain.vo.OrganizationId;
import com.example.memberservice.domain.vo.Password;
import com.example.memberservice.presentation.exception.ErrorCode;
import com.example.memberservice.presentation.exception.MemberException;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name="member")
public class Member {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    private LoginId loginId;

    @Embedded
    private Password password;

    @Embedded
    private OrganizationId organizationId;

    private String name;

    private boolean blocked;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime joinedDate;

    @Column(nullable = false)
    private int loanCount = 0;

    protected Member(){}

    public Member(String loginId, String password, Long organizationId, String name, Role role){
        this.loginId = LoginId.of(loginId);
        this.password = Password.of(password);
        this.organizationId = OrganizationId.of(organizationId);
        this.name = name;
        this.blocked = false;
        this.role = role;
        this.loanCount = 0;
    }

    public void changePassword(String currentPw, String newPw){
        if(!this.password.isMatch(currentPw))
            throw new MemberException(ErrorCode.MEMBER_PASSWORD_MISMATCH);
        this.password = Password.of(newPw);
    }

    public void changeRole(Role role){
        this.role = role;
    }

    public static Member of(String loginId, String password, Long organizationId, String name, Role role) {
        return new Member(loginId, password, organizationId, name, role);
    }

    public boolean isPasswordMatch(String rawPassword) {
        return this.password.isMatch(rawPassword);
    }

    public void increaseLoanCount() {
        this.loanCount++;
    }

    public void decreaseLoanCount() {
        if (this.loanCount > 0) {
            this.loanCount--;
        }
    }
}

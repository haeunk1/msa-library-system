package com.example.memberservice.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member,Long>{
    @Query("SELECT m FROM Member m WHERE m.loginId.loginId = :loginId")
    Optional<Member> findByLoginId(@Param("loginId") String longinId);
}

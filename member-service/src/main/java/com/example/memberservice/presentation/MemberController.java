package com.example.memberservice.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memberservice.application.MemberService;
import com.example.memberservice.application.dto.LoginRequest;
import com.example.memberservice.application.dto.LoginResponse;
import com.example.memberservice.application.dto.MemberRegisterRequest;
import com.example.memberservice.application.dto.MemberResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberservice;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody MemberRegisterRequest request){
        Long memberId = memberservice.register(request);
        return ResponseEntity.ok(memberId);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(memberservice.login(request));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("testSuccess");
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMemberInfo(@PathVariable("memberId") Long memberId){
        return ResponseEntity.ok(memberservice.getMemberInfo(memberId));
    }
}

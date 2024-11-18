package com.koreait.surl_project_11.domain.member.member.controller;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class ApiV1MemberController {
    private final MemberService memberService;

    @AllArgsConstructor
    @Getter
    public static class MemberJoinReqBody {
        @NotBlank(message = "username 입력하세요")
        private String username;
        @NotBlank(message = "password 입력하세요")
        private String password;
        @NotBlank(message = "nickname 입력하세요")
        private String nickname;
    }

    // POST /api/v1/members
    @PostMapping("")
    public RsData<Member> join(
            @RequestBody @Valid MemberJoinReqBody requestBody
    ) {
        return memberService.join(requestBody.username, requestBody.password, requestBody.nickname);
    }

}

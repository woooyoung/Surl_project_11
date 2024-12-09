package com.koreait.surl_project_11.global.initData;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.app.AppConfig;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class All {
    private final MemberService memberService;
    @Lazy
    @Autowired
    private All self;

    @Bean
    @Order(3)
    public ApplicationRunner initAll() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {

        log.debug("initAll started");

        if (memberService.count() > 0) return;

        Member memberSystem = memberService.join("system", "1234", "시스템").getData();
        if (AppConfig.isNotProd()) memberSystem.setRefreshToken(memberSystem.getUsername());
        Member memberAdmin = memberService.join("admin", "1234", "관리자").getData();
        if (AppConfig.isNotProd()) memberAdmin.setRefreshToken(memberAdmin.getUsername());
        Member member1 = memberService.join("user1", "1234", "회원 1").getData();
        if (AppConfig.isNotProd()) member1.setRefreshToken(member1.getUsername());
        Member member2 = memberService.join("user2", "1234", "회원 2").getData();
        if (AppConfig.isNotProd()) member2.setRefreshToken(member2.getUsername());
    }
}

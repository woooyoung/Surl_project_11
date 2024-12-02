package com.koreait.surl_project_11.global.initData;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import com.koreait.surl_project_11.domain.article.article.service.ArticleService;
import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.domain.surl.surl.entity.Surl;
import com.koreait.surl_project_11.domain.surl.surl.service.SurlService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

// !prod == dev or test
@Profile("!prod")
@Configuration
@RequiredArgsConstructor
public class NotProd {
    private final ArticleService articleService;
    // this를 통한 객체 내부에서의 메서드 호출은 @Transactional을 작동시키지 않아
    // 외부객에체 의한 메서드 호출은 @Transactional이 작동해
    // @Lazy, @Autowired 조합은 this의 외부 호출 모드 버전 self를 얻을 수 있다.
    // self를 통한 메서드 호출은 @Transactional을 작동 시킬 수 있어
    private final MemberService memberService;
    private final SurlService surlService;
    @Lazy
    @Autowired
    private NotProd self;

    @Bean // 개발자가 new 하지 않아도 스프링부트가 직접 관리하는 객체
    @Order(4)
    public ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (articleService.count() > 0) return;

        Member memberUser1 = memberService.findByUsername("user1").get();
        Member memberUser2 = memberService.findByUsername("user2").get();

        Article article1 = articleService.write(memberUser1, "제목 1", "내용 1").getData();
        Article article2 = articleService.write(memberUser1, "제목 2", "내용 2").getData();

        Article article3 = articleService.write(memberUser2, "제목 3", "내용 3").getData();
        Article article4 = articleService.write(memberUser2, "제목 4", "내용 4").getData();

        Surl surl1 = surlService.add(memberUser1, "네이버", "https://www.naver.com").getData();
        Surl surl2 = surlService.add(memberUser1, "다음", "https://www.daum.net").getData();

        Surl surl3 = surlService.add(memberUser2, "구글", "https://www.google.com").getData();
        Surl surl4 = surlService.add(memberUser2, "네이버", "https://www.naver.com").getData();
    }
}

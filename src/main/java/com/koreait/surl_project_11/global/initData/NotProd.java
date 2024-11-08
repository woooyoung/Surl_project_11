package com.koreait.surl_project_11.global.initData;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import com.koreait.surl_project_11.domain.article.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// !prod == dev or test
@Profile("!prod")
@Configuration
@RequiredArgsConstructor
public class NotProd {
    private final ArticleRepository articleRepository;

    @Bean // 개발자가 new 하지 않아도 스프링부트가 직접 관리하는 객체
    public ApplicationRunner initNotProd() {
        return args -> {
            System.out.println("Not Prod.initNotProd1");
            System.out.println("Not Prod.initNotProd2");
            System.out.println("Not Prod.initNotProd3");

            Article articleFirst = Article.builder().
                    title("제목1")
                    .body("내용1").build();

            Article articleSecond = Article.builder().
                    title("제목2")
                    .body("내용2").build();

            System.out.println(articleFirst.getId());
            System.out.println(articleSecond.getId());

            articleRepository.save(articleFirst);
            articleRepository.save(articleSecond);

            System.out.println(articleFirst.getId());
            System.out.println(articleSecond.getId());
        };
    }
}

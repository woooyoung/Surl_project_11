package com.koreait.surl_project_11.global.initData;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import com.koreait.surl_project_11.domain.article.article.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Optional;

// !prod == dev or test
@Profile("!prod")
@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Lazy
    @Autowired
    private NotProd self;
    // this를 통한 객체 내부에서의 메서드 호출은 @Transactional을 작동시키지 않아
    // 외부객에체 의한 메서드 호출은 @Transactional이 작동해
    // @Lazy, @Autowired 조합은 this의 외부 호출 모드 버전 self를 얻을 수 있다.
    // self를 통한 메서드 호출은 @Transactional을 작동 시킬 수 있어

    private final ArticleRepository articleRepository;

    @Bean // 개발자가 new 하지 않아도 스프링부트가 직접 관리하는 객체
    public ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if (articleRepository.count() > 0) return;

        //articleRepository.deleteAll();

        Article article1 = Article.builder().
                title("제목1")
                .body("내용1").build();

        Article article2 = Article.builder().
                title("제목2")
                .body("내용2").build();


        articleRepository.save(article1);
        articleRepository.save(article2);

        article2.setTitle("제목 2-2");

        articleRepository.delete(article1);
    }

    @Transactional
    public void work2() {
        // List : 0 ~ N
        // Optional : 0 ~ 1
        Optional<Article> opArticle = articleRepository.findById(2L); // JpaRepository 기본 제공

        List<Article> articles = articleRepository.findAll(); // JpaRepository 기본 제공

        List<Article> articlesByIn = articleRepository.findByIdInOrderByTitleDescIdAsc(List.of(1L, 2L));
        articleRepository.findByTitleContaining("제목");
        articleRepository.findByTitleAndBody("제목", "내용");
    }
}

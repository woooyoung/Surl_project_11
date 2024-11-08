package com.koreait.surl_project_11.domain.article.article.repository;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // 메서드명 기반 쿼리 -> JPA 학습용
    List<Article> findByIdInOrderByTitleDescIdAsc(List<Long> ids);

    // 메서드명 기반 쿼리
    List<Article> findByTitleContaining(String keyword);

    // 메서드명 기반 쿼리
    List<Article> findByTitleAndBody(String title, String body);
}

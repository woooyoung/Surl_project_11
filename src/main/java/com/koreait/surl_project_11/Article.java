package com.koreait.surl_project_11;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
    private String body2;
    private String body3;
    private String body4;
}

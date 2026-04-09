package com.example.oliveold.controller;

import com.example.oliveold.dto.ArticleForm;
import com.example.oliveold.entity.Article;
import com.example.oliveold.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){

        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createAriticle(ArticleForm form){
        log.info(form.toString());

        // 1. DTO to Entity
        Article article = form.toEntity();
        // 2. Repository에게 Entity를 DB안에 저장하게함

        Article saved = articleRepository.save(article);
        log.info(article.toString());
        return "";
    }
}

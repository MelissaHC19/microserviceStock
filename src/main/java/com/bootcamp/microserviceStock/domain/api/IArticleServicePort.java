package com.bootcamp.microserviceStock.domain.api;

import com.bootcamp.microserviceStock.domain.model.Article;

public interface IArticleServicePort {
    void createArticle(Article article);
}

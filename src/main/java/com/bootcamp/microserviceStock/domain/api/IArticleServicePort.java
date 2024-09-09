package com.bootcamp.microserviceStock.domain.api;

import com.bootcamp.microserviceStock.domain.model.Article;
import com.bootcamp.microserviceStock.domain.util.Pagination;

public interface IArticleServicePort {
    void createArticle(Article article);
    Pagination<Article> listArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}

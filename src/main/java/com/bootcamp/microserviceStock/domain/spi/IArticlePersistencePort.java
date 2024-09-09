package com.bootcamp.microserviceStock.domain.spi;

import com.bootcamp.microserviceStock.domain.model.Article;
import com.bootcamp.microserviceStock.domain.util.Pagination;

public interface IArticlePersistencePort {
    void createArticle(Article article);
    boolean alreadyExistsByName(String name);
    Pagination<Article> listArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}

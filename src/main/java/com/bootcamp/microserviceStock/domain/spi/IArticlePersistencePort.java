package com.bootcamp.microserviceStock.domain.spi;

import com.bootcamp.microserviceStock.domain.model.Article;

public interface IArticlePersistencePort {
    void createArticle(Article article);
    boolean alreadyExistsByName(String name);
}

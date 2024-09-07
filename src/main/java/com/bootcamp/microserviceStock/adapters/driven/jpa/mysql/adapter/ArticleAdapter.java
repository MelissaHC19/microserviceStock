package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.bootcamp.microserviceStock.domain.model.Article;
import com.bootcamp.microserviceStock.domain.spi.IArticlePersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticleAdapter implements IArticlePersistencePort {
    private final IArticleRepository articleRepository;
    private final IArticleEntityMapper articleEntityMapper;

    @Override
    public void createArticle(Article article) {
        articleRepository.save(articleEntityMapper.articleToEntity(article));
    }

    @Override
    public boolean alreadyExistsByName(String name) {
        return articleRepository.findByName(name).isPresent();
    }
}

package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.bootcamp.microserviceStock.domain.model.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IArticleEntityMapper {
    ArticleEntity articleToEntity(Article article);
}
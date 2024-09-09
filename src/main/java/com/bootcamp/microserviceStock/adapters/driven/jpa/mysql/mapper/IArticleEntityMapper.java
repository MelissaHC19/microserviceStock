package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.bootcamp.microserviceStock.domain.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IArticleEntityMapper {
    @Mapping(target = "categoryEntityList", source = "categoryList")
    ArticleEntity articleToEntity(Article article);

    @Mapping(target = "categoryList", source = "categoryEntityList")
    Article entityToDomain(ArticleEntity articleEntity);
}
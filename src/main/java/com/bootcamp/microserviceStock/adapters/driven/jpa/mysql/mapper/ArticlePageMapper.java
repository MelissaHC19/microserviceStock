package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.bootcamp.microserviceStock.domain.model.Article;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class ArticlePageMapper {
    private final IArticleEntityMapper articleEntityMapper;

    public Pagination<Article> pageToPagination(Page<ArticleEntity> page) {
        Pagination<Article> pagination = new Pagination<>();
        pagination.setContent(page.getContent().stream()
                .map(articleEntityMapper::entityToDomain)
                .toList()
        );
        pagination.setPageNumber(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        return pagination;
    }
}
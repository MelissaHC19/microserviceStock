package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class CategoryPageMapper {
    private final ICategoryEntityMapper categoryEntityMapper;

    public Pagination<Category> pageToPagination(Page<CategoryEntity> page) {
        Pagination<Category> pagination = new Pagination<>();
        pagination.setContent(page.getContent().stream()
                .map(categoryEntityMapper::entityToDomain)
                .toList()
        );
        pagination.setPageNumber(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        return pagination;
    }
}
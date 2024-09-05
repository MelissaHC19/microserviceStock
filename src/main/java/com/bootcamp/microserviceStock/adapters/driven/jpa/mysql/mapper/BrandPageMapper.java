package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class BrandPageMapper {
    private final IBrandEntityMapper brandEntityMapper;

    public Pagination<Brand> pageToPagination(Page<BrandEntity> page) {
        Pagination<Brand> pagination = new Pagination<>();
        pagination.setContent(page.getContent().stream()
                .map(brandEntityMapper::entityToDomain)
                .toList()
        );
        pagination.setPageNumber(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        return pagination;
    }
}

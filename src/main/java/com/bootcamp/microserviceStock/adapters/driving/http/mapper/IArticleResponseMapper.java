package com.bootcamp.microserviceStock.adapters.driving.http.mapper;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.ArticleResponse;
import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.PaginationResponse;
import com.bootcamp.microserviceStock.domain.model.Article;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface IArticleResponseMapper {
    @Mapping(source = "categoryList", target = "categoryList", qualifiedByName = "mapCategoryListToStringList")
    @Mapping(source = "brand", target = "brand", qualifiedByName = "mapBrandToString")
    ArticleResponse articleToResponse(Article article);

    @Named("mapCategoryListToStringList")
    default List<Map<String, Object>> mapCategoryListToStringList(List<Category> categoryList) {
        return categoryList.stream()
                .map(category -> {
                    Map<String, Object> categoryMap = new LinkedHashMap<>();
                    categoryMap.put("id", category.getId());
                    categoryMap.put("name", category.getName());
                    return categoryMap;
                })
                .toList();
    }

    @Named("mapBrandToString")
    default String mapBrandToString(Brand brand) {
        return brand.getName();
    }

    default PaginationResponse<ArticleResponse> paginationToPaginationResponse(Pagination<Article> pagination) {
        List<ArticleResponse> articleResponses = pagination.getContent().stream()
                .map(this::articleToResponse)
                .toList();
        Pagination<ArticleResponse> responsePagination = new Pagination<>(
                articleResponses,
                pagination.getPageNumber(),
                pagination.getPageSize(),
                pagination.getTotalElements());
        return toPaginationResponse(responsePagination);
    }

    @Mapping(target = "page", source = "pagination.pageNumber")
    @Mapping(target = "size", source = "pagination.pageSize")
    @Mapping(target = "totalElements", source = "pagination.totalElements")
    @Mapping(target = "totalPages", source = "pagination.totalPages")
    @Mapping(target = "first", source = "pagination.first")
    @Mapping(target = "last", source = "pagination.last")
    @Mapping(target = "empty", source = "pagination.empty")
    @Mapping(target = "content", source = "pagination.content")
    PaginationResponse<ArticleResponse> toPaginationResponse(Pagination<ArticleResponse> pagination);
}

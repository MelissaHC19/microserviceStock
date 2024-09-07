package com.bootcamp.microserviceStock.adapters.driving.http.mapper;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.request.ArticleRequest;
import com.bootcamp.microserviceStock.domain.model.Article;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IArticleRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "brandID", target = "brand", qualifiedByName = "mapBrandIDToBrand")
    @Mapping(source = "categoryIDs", target = "categoryList", qualifiedByName = "mapCategoryIDsToCategory")
    Article requestToArticle(ArticleRequest articleRequest);

    @Named("mapBrandIDToBrand")
    default Brand mapBrandIDToBrand(Long brandID) {
        return new Brand(brandID, null, null);
    }

    @Named("mapCategoryIDsToCategory")
    default List<Category> mapCategoryIDsToCategory(List<Long> categoryIDs) {
        return categoryIDs.stream().map(id -> new Category(id, null, null)).toList();
    }
}

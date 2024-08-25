package com.bootcamp.microserviceStock.adapters.driving.http.mapper;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.CategoryResponse;
import com.bootcamp.microserviceStock.domain.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {
    CategoryResponse categoryToResponse(Category category);
}

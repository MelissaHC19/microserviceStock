package com.bootcamp.microserviceStock.adapters.driving.http.mapper;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.CategoryResponse;
import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.PaginationResponse;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {
    CategoryResponse categoryToResponse(Category category);

    default PaginationResponse<CategoryResponse> paginationToPaginationResponse(Pagination<Category> pagination) {
        List<CategoryResponse> categoryResponses = pagination.getContent().stream()
                .map(this::categoryToResponse)
                .toList();
        Pagination<CategoryResponse> responsePagination = new Pagination<>(
                categoryResponses,
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
    PaginationResponse<CategoryResponse> toPaginationResponse(Pagination<CategoryResponse> pagination);
}
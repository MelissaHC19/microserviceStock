package com.bootcamp.microserviceStock.adapters.driving.http.mapper;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.BrandResponse;
import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.PaginationResponse;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBrandResponseMapper {
    BrandResponse brandToResponse(Brand brand);

    default PaginationResponse<BrandResponse> paginationToPaginationResponse(Pagination<Brand> pagination) {
        List<BrandResponse> brandResponses = pagination.getContent().stream()
                .map(this::brandToResponse)
                .toList();
        Pagination<BrandResponse> responsePagination = new Pagination<>(
                brandResponses,
                pagination.getPageNumber(),
                pagination.getPageSize(),
                pagination.getTotalElements(),
                pagination.getTotalPages());
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
    PaginationResponse<BrandResponse> toPaginationResponse(Pagination<BrandResponse> pagination);
}
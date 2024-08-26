package com.bootcamp.microserviceStock.adapters.driving.http.mapper;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.request.BrandRequest;
import com.bootcamp.microserviceStock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBrandRequestMapper {
    @Mapping(target = "id", ignore = true)
    Brand requestToBrand(BrandRequest brandRequest);
}
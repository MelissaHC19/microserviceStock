package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamp.microserviceStock.domain.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBrandEntityMapper {
    BrandEntity brandToEntity(Brand brand);
}

package com.bootcamp.microserviceStock.domain.api;

import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.util.Pagination;

public interface IBrandServicePort {
    void createBrand(Brand brand);
    Pagination<Brand> listBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
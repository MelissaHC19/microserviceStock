package com.bootcamp.microserviceStock.domain.spi;

import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.util.Pagination;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    boolean alreadyExistsByName(String name);
    Pagination<Brand> listBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
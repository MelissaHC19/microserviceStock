package com.bootcamp.microserviceStock.domain.spi;

import com.bootcamp.microserviceStock.domain.model.Brand;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
}
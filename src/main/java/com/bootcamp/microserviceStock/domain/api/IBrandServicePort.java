package com.bootcamp.microserviceStock.domain.api;

import com.bootcamp.microserviceStock.domain.model.Brand;

public interface IBrandServicePort {
    void createBrand(Brand brand);
}
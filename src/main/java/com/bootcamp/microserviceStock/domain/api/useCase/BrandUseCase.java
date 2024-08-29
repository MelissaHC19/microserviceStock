package com.bootcamp.microserviceStock.domain.api.useCase;

import com.bootcamp.microserviceStock.domain.api.IBrandServicePort;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.spi.IBrandPersistencePort;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createBrand(Brand brand) {
        brandPersistencePort.createBrand(brand);
    }
}
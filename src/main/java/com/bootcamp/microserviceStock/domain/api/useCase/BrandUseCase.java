package com.bootcamp.microserviceStock.domain.api.useCase;

import com.bootcamp.microserviceStock.domain.api.IBrandServicePort;
import com.bootcamp.microserviceStock.domain.exception.ValidationException;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.spi.IBrandPersistencePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;

import java.util.ArrayList;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createBrand(Brand brand) {
        ArrayList<String> errors = new ArrayList<>();

        if (brand.getName().trim().isEmpty()) {
            errors.add(DomainConstants.FIELD_NAME_EMPTY_MESSAGE);
        }
        if (brand.getDescription().trim().isEmpty()) {
            errors.add(DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        }
        if (brand.getName().length() > DomainConstants.MAX_FIELD_SIZE_NAME) {
            errors.add(DomainConstants.MAX_FIELD_SIZE_NAME_MESSAGE);
        }
        if (brand.getDescription().length() > DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_BRAND) {
            errors.add(DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_BRAND_MESSAGE);
        }
        if(brandPersistencePort.alreadyExistsByName(brand.getName())) {
            errors.add(DomainConstants.BRAND_CREATED_MESSAGE);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        brandPersistencePort.createBrand(brand);
    }
}
package com.bootcamp.microserviceStock.domain.api.useCase;

import com.bootcamp.microserviceStock.domain.api.IBrandServicePort;
import com.bootcamp.microserviceStock.domain.exception.ValidationException;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.spi.IBrandPersistencePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import com.bootcamp.microserviceStock.domain.util.Pagination;

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
            errors.add(DomainConstants.BRAND_ALREADY_EXISTS_MESSAGE);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        brandPersistencePort.createBrand(brand);
    }

    @Override
    public Pagination<Brand> listBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        ArrayList<String> errors = new ArrayList<>();

        if (pageNumber == null) {
            errors.add(DomainConstants.PAGE_NUMBER_NULL_MESSAGE);
        } else if (pageNumber < 0) {
            errors.add(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE);
        }
        if (pageSize == null) {
            errors.add(DomainConstants.PAGE_SIZE_NULL_MESSAGE);
        } else if (pageSize <= 0) {
            errors.add(DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
        }
        if (sortBy == null || !sortBy.equalsIgnoreCase(DomainConstants.VALID_SORT_BY_FIELD)) {
            errors.add(DomainConstants.INVALID_SORT_BY_FIELD_MESSAGE);
        }
        if (!sortDirection.equalsIgnoreCase(DomainConstants.SORT_DIRECTION_ASC) && !sortDirection.equalsIgnoreCase(DomainConstants.SORT_DIRECTION_DESC)) {
            errors.add(DomainConstants.INVALID_SORT_DIRECTION_MESSAGE);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        return brandPersistencePort.listBrands(pageNumber, pageSize, sortBy, sortDirection);
    }
}
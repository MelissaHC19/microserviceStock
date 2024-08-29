package com.bootcamp.microserviceStock.domain.api.useCase;

import com.bootcamp.microserviceStock.domain.api.ICategoryServicePort;
import com.bootcamp.microserviceStock.domain.exception.EmptyFieldException;
import com.bootcamp.microserviceStock.domain.exception.MaxFieldSizeException;
import com.bootcamp.microserviceStock.domain.exception.ValidationException;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.spi.ICategoryPersistencePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;

import java.util.ArrayList;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createCategory(Category category) {
        ArrayList<String> errors = new ArrayList<>();

        if (category.getName().trim().isEmpty()) {
            errors.add(DomainConstants.FIELD_NAME_EMPTY_MESSAGE);
        }
        if (category.getDescription().trim().isEmpty()) {
            errors.add(DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        }
        if (category.getName().length() > DomainConstants.MAX_FIELD_SIZE_NAME) {
            errors.add(DomainConstants.MAX_FIELD_SIZE_NAME_MESSAGE);
        }
        if (category.getDescription().length() > DomainConstants.MAX_FIELD_SIZE_DESCRIPTION) {
            errors.add(DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_MESSAGE);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        categoryPersistencePort.createCategory(category);
    }
}
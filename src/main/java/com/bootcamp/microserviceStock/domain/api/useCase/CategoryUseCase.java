package com.bootcamp.microserviceStock.domain.api.useCase;

import com.bootcamp.microserviceStock.domain.api.ICategoryServicePort;
import com.bootcamp.microserviceStock.domain.exception.ValidationException;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.spi.ICategoryPersistencePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import com.bootcamp.microserviceStock.domain.util.Pagination;

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
        if(categoryPersistencePort.alreadyExistsByName(category.getName())) {
            errors.add(DomainConstants.CATEGORY_ALREADY_EXISTS_MESSAGE);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        categoryPersistencePort.createCategory(category);
    }

    @Override
    public Pagination<Category> listCategories(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        ArrayList<String> errors = new ArrayList<>();

        if (pageNumber < 0) {
            errors.add(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE);
        }
        if (pageSize <= 0) {
            errors.add(DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
        }
        if (!sortBy.equalsIgnoreCase(DomainConstants.VALID_SORT_BY_FIELD)) {
            errors.add(DomainConstants.INVALID_SORT_BY_FIELD_MESSAGE);
        }
        if (!sortDirection.equalsIgnoreCase(DomainConstants.SORT_DIRECTION_ASC) && !sortDirection.equalsIgnoreCase(DomainConstants.SORT_DIRECTION_DESC)) {
            errors.add(DomainConstants.INVALID_SORT_DIRECTION_MESSAGE);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        return categoryPersistencePort.listCategories(pageNumber, pageSize, sortBy, sortDirection);
    }
}
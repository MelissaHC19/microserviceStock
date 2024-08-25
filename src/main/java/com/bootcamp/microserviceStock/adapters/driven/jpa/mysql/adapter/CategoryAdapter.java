package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.util.DrivenConstants;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public void createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistsException(DrivenConstants.CATEGORY_ALREADY_EXISTS_MESSAGE);
        }

        categoryRepository.save(categoryEntityMapper.categoryToEntity(category));
    }
}
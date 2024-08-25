package com.bootcamp.microserviceStock.domain.spi;

import com.bootcamp.microserviceStock.domain.model.Category;

public interface ICategoryPersistencePort {
    void createCategory(Category category);
}

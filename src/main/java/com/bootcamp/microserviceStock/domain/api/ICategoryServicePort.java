package com.bootcamp.microserviceStock.domain.api;

import com.bootcamp.microserviceStock.domain.model.Category;

public interface ICategoryServicePort {
    void createCategory(Category category);
}

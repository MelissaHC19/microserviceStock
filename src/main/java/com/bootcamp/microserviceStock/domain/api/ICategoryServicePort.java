package com.bootcamp.microserviceStock.domain.api;

import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.util.Pagination;

public interface ICategoryServicePort {
    void createCategory(Category category);
    Pagination<Category> listCategories(int pageNumber, int pageSize, String sortBy, String sortDirection);
}
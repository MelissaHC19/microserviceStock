package com.bootcamp.microserviceStock.domain.spi;

import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.util.Pagination;

public interface ICategoryPersistencePort {
    void createCategory(Category category);
    boolean alreadyExistsByName(String name);
    Pagination<Category> listCategories(int pageNumber, int pageSize, String sortBy, String sortDirection);
}

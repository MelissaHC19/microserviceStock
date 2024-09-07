package com.bootcamp.microserviceStock.domain.spi;

import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.util.Pagination;

public interface ICategoryPersistencePort {
    void createCategory(Category category);
    boolean alreadyExistsByName(String name);
    boolean alreadyExistsByID(Long id);
    Pagination<Category> listCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}

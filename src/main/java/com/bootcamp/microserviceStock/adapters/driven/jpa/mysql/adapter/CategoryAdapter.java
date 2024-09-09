package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.CategoryPageMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.spi.ICategoryPersistencePort;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.categoryToEntity(category));
    }

    @Override
    public boolean alreadyExistsByName(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

    @Override
    public boolean alreadyExistsByID(Long id) {
        return categoryRepository.findById(id).isPresent();
    }

    @Override
    public Pagination<Category> listCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<CategoryEntity> page = categoryRepository.findAll(pageable);
        CategoryPageMapper categoryPageMapper = new CategoryPageMapper(categoryEntityMapper);
        return categoryPageMapper.pageToPagination(page);
    }
}
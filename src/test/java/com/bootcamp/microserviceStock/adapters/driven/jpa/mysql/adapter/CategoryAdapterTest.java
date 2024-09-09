package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.CategoryPageMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryAdapterTest {
    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryAdapter categoryAdapter;

    private CategoryPageMapper categoryPageMapper;

    @BeforeEach
    void setUp() {
        categoryPageMapper = new CategoryPageMapper(categoryEntityMapper);
        categoryAdapter = new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Test
    @DisplayName("Validation that a category is created correctly in the DB")
    void createCategory() {
        Category category = new Category(1L, "Beauty & Personal Care", "Discover skincare, makeup, and wellness products from top brands.");
        CategoryEntity categoryEntity = categoryEntityMapper.categoryToEntity(category);
        given(categoryRepository.save(categoryEntity)).willReturn(categoryEntity);
        categoryAdapter.createCategory(category);
        verify(categoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    @DisplayName("Validation when category already exists in the DB")
    void alreadyExistsByNameTrue() {
        String name = "Beauty & Personal Care";
        Mockito.when(categoryRepository.findByName(name)).thenReturn(Optional.of(new CategoryEntity()));
        boolean exists = categoryAdapter.alreadyExistsByName(name);
        assertTrue(exists);
        verify(categoryRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Validation when a category doesn't exists in the DB")
    void alreadyExistsByNameFalse() {
        String name = "Books";
        Mockito.when(categoryRepository.findByName(name)).thenReturn(Optional.empty());
        boolean exists = categoryAdapter.alreadyExistsByName(name);
        assertFalse(exists);
        verify(categoryRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Validation that it returns paginated categories 'asc'")
    void listCategoriesAsc() {
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "asc";

        CategoryEntity categoryEntity1 = new CategoryEntity(1L, "Books", "Browse a vast collection of books across all genres and interests.");
        CategoryEntity categoryEntity2 = new CategoryEntity(2L, "Beauty & Personal Care", "Discover skincare, makeup, and wellness products from top brands.");

        List<CategoryEntity> categoryEntities = List.of(categoryEntity2, categoryEntity1);
        Page<CategoryEntity> page = new PageImpl<>(categoryEntities, PageRequest.of(pageNumber, pageSize), 2);
        Mockito.when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(categoryEntityMapper.entityToDomain(categoryEntity1)).thenReturn(new Category(1L, "Books", "Browse a vast collection of books across all genres and interests."));
        Mockito.when(categoryEntityMapper.entityToDomain(categoryEntity2)).thenReturn(new Category(2L, "Beauty & Personal Care", "Discover skincare, makeup, and wellness products from top brands."));
        Pagination<Category> result = categoryAdapter.listCategories(pageNumber, pageSize, sortBy, sortDirection);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Validation when there aren't categories")
    void listCategoriesShouldReturnEmptyPaginationWhenNoCategoriesExists() {
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "asc";

        Page<CategoryEntity> page = new PageImpl<>(List.of(), PageRequest.of(pageNumber, pageSize), 0);
        Mockito.when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        Pagination<Category> result = categoryAdapter.listCategories(pageNumber, pageSize, sortBy, sortDirection);
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        assertThat(result.getTotalElements()).isZero();
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Validation that it returns paginated categories 'desc'")
    void listCategoriesDesc() {
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "desc";

        CategoryEntity categoryEntity1 = new CategoryEntity(1L, "Books", "Browse a vast collection of books across all genres and interests.");
        CategoryEntity categoryEntity2 = new CategoryEntity(2L, "Beauty & Personal Care", "Discover skincare, makeup, and wellness products from top brands.");

        List<CategoryEntity> categoryEntities = List.of(categoryEntity1, categoryEntity2);
        Page<CategoryEntity> page = new PageImpl<>(categoryEntities, PageRequest.of(pageNumber, pageSize), 2);
        Mockito.when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(categoryEntityMapper.entityToDomain(categoryEntity1)).thenReturn(new Category(1L, "Books", "Browse a vast collection of books across all genres and interests."));
        Mockito.when(categoryEntityMapper.entityToDomain(categoryEntity2)).thenReturn(new Category(2L, "Beauty & Personal Care", "Discover skincare, makeup, and wellness products from top brands."));
        Pagination<Category> result = categoryAdapter.listCategories(pageNumber, pageSize, sortBy, sortDirection);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }
}
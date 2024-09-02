package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamp.microserviceStock.domain.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
}
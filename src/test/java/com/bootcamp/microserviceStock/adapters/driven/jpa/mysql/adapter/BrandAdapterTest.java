package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.util.Pagination;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BrandAdapterTest {
    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private IBrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandAdapter brandAdapter;

    @Test
    @DisplayName("Validation that a brand is created correctly in the DB")
    void createBrand() {
        Brand brand = new Brand(1L, "FitLife", "Quality workout gear, apparel, and supplements to support your active lifestyle.");
        BrandEntity brandEntity = brandEntityMapper.brandToEntity(brand);
        given(brandRepository.save(brandEntity)).willReturn(brandEntity);
        brandAdapter.createBrand(brand);
        verify(brandRepository, times(1)).save(brandEntity);
    }

    @Test
    @DisplayName("Validation when brand already exists in the DB")
    void alreadyExistsByNameTrue() {
        String name = "FitLife";
        Mockito.when(brandRepository.findByName(name)).thenReturn(Optional.of(new BrandEntity()));
        boolean exists = brandAdapter.alreadyExistsByName(name);
        assertTrue(exists);
        verify(brandRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Validation when brand doesn't exists in the DB")
    void alreadyExistsByNameFalse() {
        String name = "UrbanEdge";
        Mockito.when(brandRepository.findByName(name)).thenReturn(Optional.empty());
        boolean exists = brandAdapter.alreadyExistsByName(name);
        assertFalse(exists);
        verify(brandRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Validation that it returns paginated brands 'asc'")
    void listBrandsAsc() {
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "asc";

        BrandEntity brandEntity1 = new BrandEntity(1L, "TechNova", "Innovative electronics that combine style and functionality, from smartphones to smart home gadgets.");
        BrandEntity brandEntity2 = new BrandEntity(2L, "EcoNest", "Eco-friendly home products, from organic bedding to energy-efficient appliances.");

        List<BrandEntity> brandEntities = List.of(brandEntity2, brandEntity1);
        Page<BrandEntity> page = new PageImpl<>(brandEntities, PageRequest.of(pageNumber, pageSize), 2);
        Mockito.when(brandRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(brandEntityMapper.entityToDomain(brandEntity1)).thenReturn(new Brand(1L, "Books", "Browse a vast collection of books across all genres and interests."));
        Mockito.when(brandEntityMapper.entityToDomain(brandEntity2)).thenReturn(new Brand(2L, "Beauty & Personal Care", "Discover skincare, makeup, and wellness products from top brands."));
        Pagination<Brand> result = brandAdapter.listBrands(pageNumber, pageSize, sortBy, sortDirection);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        verify(brandRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Validation when there aren't brands")
    void listBrandsShouldReturnEmptyPaginationWhenNoBrandsExists() {
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "asc";

        Page<BrandEntity> page = new PageImpl<>(List.of(), PageRequest.of(pageNumber, pageSize), 0);
        Mockito.when(brandRepository.findAll(any(Pageable.class))).thenReturn(page);
        Pagination<Brand> result = brandAdapter.listBrands(pageNumber, pageSize, sortBy, sortDirection);
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        assertThat(result.getTotalElements()).isZero();
        verify(brandRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Validation that it returns paginated brands 'desc'")
    void listBrandsDesc() {
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "desc";

        BrandEntity brandEntity1 = new BrandEntity(1L, "TechNova", "Innovative electronics that combine style and functionality, from smartphones to smart home gadgets.");
        BrandEntity brandEntity2 = new BrandEntity(2L, "EcoNest", "Eco-friendly home products, from organic bedding to energy-efficient appliances.");

        List<BrandEntity> brandEntities = List.of(brandEntity1, brandEntity2);
        Page<BrandEntity> page = new PageImpl<>(brandEntities, PageRequest.of(pageNumber, pageSize), 2);
        Mockito.when(brandRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(brandEntityMapper.entityToDomain(brandEntity1)).thenReturn(new Brand(1L, "Books", "Browse a vast collection of books across all genres and interests."));
        Mockito.when(brandEntityMapper.entityToDomain(brandEntity2)).thenReturn(new Brand(2L, "Beauty & Personal Care", "Discover skincare, makeup, and wellness products from top brands."));
        Pagination<Brand> result = brandAdapter.listBrands(pageNumber, pageSize, sortBy, sortDirection);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        verify(brandRepository, times(1)).findAll(any(Pageable.class));
    }
}
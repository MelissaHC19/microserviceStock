package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.bootcamp.microserviceStock.domain.model.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
}
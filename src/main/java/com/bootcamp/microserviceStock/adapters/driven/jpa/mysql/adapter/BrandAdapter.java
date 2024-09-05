package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.BrandPageMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.spi.IBrandPersistencePort;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class BrandAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public void createBrand(Brand brand) {
        brandRepository.save(brandEntityMapper.brandToEntity(brand));
    }

    @Override
    public boolean alreadyExistsByName(String name) {
        return brandRepository.findByName(name).isPresent();
    }

    @Override
    public Pagination<Brand> listBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<BrandEntity> page = brandRepository.findAll(pageable);
        BrandPageMapper brandPageMapper = new BrandPageMapper(brandEntityMapper);
        return brandPageMapper.pageToPagination(page);
    }
}
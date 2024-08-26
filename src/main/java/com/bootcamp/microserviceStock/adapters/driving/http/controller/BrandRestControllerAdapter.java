package com.bootcamp.microserviceStock.adapters.driving.http.controller;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.request.BrandRequest;
import com.bootcamp.microserviceStock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.bootcamp.microserviceStock.domain.api.IBrandServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;

    @PostMapping("/create")
    public ResponseEntity<Void> createBrand(@RequestBody BrandRequest request) {
        brandServicePort.createBrand(brandRequestMapper.requestToBrand(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
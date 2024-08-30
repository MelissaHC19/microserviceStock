package com.bootcamp.microserviceStock.adapters.driving.http.controller;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.request.BrandRequest;
import com.bootcamp.microserviceStock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.bootcamp.microserviceStock.domain.api.IBrandServicePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;

    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> createBrand(@Valid @RequestBody BrandRequest request) {
        brandServicePort.createBrand(brandRequestMapper.requestToBrand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(DomainConstants.BRAND_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }
}
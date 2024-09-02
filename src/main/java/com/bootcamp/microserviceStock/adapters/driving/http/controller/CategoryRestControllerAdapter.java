package com.bootcamp.microserviceStock.adapters.driving.http.controller;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.request.CategoryRequest;
import com.bootcamp.microserviceStock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.bootcamp.microserviceStock.domain.api.ICategoryServicePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;

    @Operation(summary = "Create category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Category not created",
                    content = @Content),
    })
    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        categoryServicePort.createCategory(categoryRequestMapper.requestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(DomainConstants.CATEGORY_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }
}
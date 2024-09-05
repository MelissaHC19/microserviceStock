package com.bootcamp.microserviceStock.adapters.driving.http.controller;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.request.BrandRequest;
import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.BrandResponse;
import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.PaginationResponse;
import com.bootcamp.microserviceStock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.bootcamp.microserviceStock.adapters.driving.http.mapper.IBrandResponseMapper;
import com.bootcamp.microserviceStock.configuration.exceptionHandler.ExceptionResponse;
import com.bootcamp.microserviceStock.domain.api.IBrandServicePort;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;

    @Operation(summary = "Create brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created successfully",
                    content = @Content(schema = @Schema(implementation = ControllerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Brand not created",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> createBrand(@Valid @RequestBody BrandRequest request) {
        brandServicePort.createBrand(brandRequestMapper.requestToBrand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(DomainConstants.BRAND_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }

    @Operation(summary = "List brands (pagination)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved brands",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @GetMapping
    public ResponseEntity<PaginationResponse<BrandResponse>> listBrands(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Pagination<Brand> brandPagination = brandServicePort.listBrands(page, size, sortBy, sortDirection);
        PaginationResponse<BrandResponse> response = brandResponseMapper.paginationToPaginationResponse(brandPagination);
        return ResponseEntity.ok(response);
    }
}
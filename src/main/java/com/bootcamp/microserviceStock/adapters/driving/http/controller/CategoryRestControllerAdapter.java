package com.bootcamp.microserviceStock.adapters.driving.http.controller;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.request.CategoryRequest;
import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.CategoryResponse;
import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.PaginationResponse;
import com.bootcamp.microserviceStock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.bootcamp.microserviceStock.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.bootcamp.microserviceStock.configuration.exceptionHandler.ExceptionResponse;
import com.bootcamp.microserviceStock.domain.api.ICategoryServicePort;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @Operation(summary = "Create category",
            tags = {"Category"},
            description = "This endpoint allows the creation of a new category. The category name and description must " +
                    "follow specific length requirements and cannot be empty, and a category with the same name must not " +
                    "already exist. If any of these conditions are violated, the request will result in an error response " +
                    "detailing the validation issues."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(schema = @Schema(implementation = ControllerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Category not created",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        categoryServicePort.createCategory(categoryRequestMapper.requestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(DomainConstants.CATEGORY_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }

    @Operation(summary = "List categories (pagination)",
            tags = {"Category"},
            description = "This endpoint retrieves a paginated list of categories with optional sorting. " +
                    "It validates the provided parameters to ensure proper pagination and sorting behavior. " +
                    "If any of the input parameters violate the business rules, the request will result in an " +
                    "error response explaining the issues."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @GetMapping
    public ResponseEntity<PaginationResponse<CategoryResponse>> listCategories(
            @Parameter(description = "Page number to retrieve (starting from 0)")
            @RequestParam(required = false) Integer page,
            @Parameter(description = "Number of categories per page")
            @RequestParam(required = false) Integer size,
            @Parameter(description = "Sorting criteria, field by which to sort categories 'name'")
            @RequestParam(required = false) String sortBy,
            @Parameter(description = "Sorting order 'asc' or 'desc'")
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Pagination<Category> categoryPagination = categoryServicePort.listCategories(page, size, sortBy, sortDirection);
        PaginationResponse<CategoryResponse> response = categoryResponseMapper.paginationToPaginationResponse(categoryPagination);
        return ResponseEntity.ok(response);
    }
}
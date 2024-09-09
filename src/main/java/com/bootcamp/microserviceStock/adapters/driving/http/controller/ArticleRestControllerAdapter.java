package com.bootcamp.microserviceStock.adapters.driving.http.controller;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.request.ArticleRequest;
import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.ArticleResponse;
import com.bootcamp.microserviceStock.adapters.driving.http.dto.response.PaginationResponse;
import com.bootcamp.microserviceStock.adapters.driving.http.mapper.IArticleRequestMapper;
import com.bootcamp.microserviceStock.adapters.driving.http.mapper.IArticleResponseMapper;
import com.bootcamp.microserviceStock.configuration.exceptionHandler.ExceptionResponse;
import com.bootcamp.microserviceStock.domain.api.IArticleServicePort;
import com.bootcamp.microserviceStock.domain.model.Article;
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
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleRestControllerAdapter {
    private final IArticleServicePort articleServicePort;
    private final IArticleRequestMapper articleRequestMapper;
    private final IArticleResponseMapper articleResponseMapper;

    @Operation(summary = "Create article",
            tags = {"Article"},
            description = "This endpoint allows the creation of a new article. The article name and description must " +
                    "follow specific length requirements and cannot be empty, and an article with the same name must not " +
                    "already exist. If any of these conditions are violated, the request will result in an error response " +
                    "detailing the validation issues."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created successfully",
                    content = @Content(schema = @Schema(implementation = ControllerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Article not created",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> createArticle(@Valid @RequestBody ArticleRequest request) {
        articleServicePort.createArticle(articleRequestMapper.requestToArticle(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(DomainConstants.ARTICLE_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }

    @Operation(summary = "List articles (pagination)",
            tags = {"Article"},
            description = "This endpoint retrieves a paginated list of articles with optional sorting. It validates " +
                    "the provided parameters to ensure proper pagination and sorting behavior. If any of the input " +
                    "parameters violate the business rules, the request will result in an error response explaining " +
                    "the issues."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved articles",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @GetMapping
    public ResponseEntity<PaginationResponse<ArticleResponse>> listArticles(
            @Parameter(description = "Page number to retrieve (starting from 0)")
            @RequestParam(required = false) Integer page,
            @Parameter(description = "Number of articles per page")
            @RequestParam(required = false) Integer size,
            @Parameter(description = "Sorting criteria, field by which to sort articles 'name', 'brandName', 'categoryName'")
            @RequestParam(required = false) String sortBy,
            @Parameter(description = "Sorting order 'asc' or 'desc'")
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Pagination<Article> articlePagination = articleServicePort.listArticles(page, size, sortBy, sortDirection);
        PaginationResponse<ArticleResponse> response = articleResponseMapper.paginationToPaginationResponse(articlePagination);
        return ResponseEntity.ok(response);
    }
}
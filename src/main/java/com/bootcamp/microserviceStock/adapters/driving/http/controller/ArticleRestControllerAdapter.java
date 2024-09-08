package com.bootcamp.microserviceStock.adapters.driving.http.controller;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.request.ArticleRequest;
import com.bootcamp.microserviceStock.adapters.driving.http.mapper.IArticleRequestMapper;
import com.bootcamp.microserviceStock.configuration.exceptionHandler.ExceptionResponse;
import com.bootcamp.microserviceStock.domain.api.IArticleServicePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleRestControllerAdapter {
    private final IArticleServicePort articleServicePort;
    private final IArticleRequestMapper articleRequestMapper;

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
}

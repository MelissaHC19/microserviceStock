package com.bootcamp.microserviceStock.adapters.driving.http.controller;

import com.bootcamp.microserviceStock.adapters.driving.http.dto.request.ArticleRequest;
import com.bootcamp.microserviceStock.adapters.driving.http.mapper.IArticleRequestMapper;
import com.bootcamp.microserviceStock.domain.api.IArticleServicePort;
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
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleRestControllerAdapter {
    private final IArticleServicePort articleServicePort;
    private final IArticleRequestMapper articleRequestMapper;

    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> createArticle(@Valid @RequestBody ArticleRequest request) {
        articleServicePort.createArticle(articleRequestMapper.requestToArticle(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(DomainConstants.ARTICLE_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }
}

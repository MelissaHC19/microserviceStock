package com.bootcamp.microserviceStock.adapters.driving.http.dto.response;

import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class ArticleResponse {
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private String brand;
    private List<Map<String, Object>> categoryList;
}

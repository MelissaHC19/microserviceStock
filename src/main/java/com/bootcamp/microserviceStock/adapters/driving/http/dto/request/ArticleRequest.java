package com.bootcamp.microserviceStock.adapters.driving.http.dto.request;

import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class ArticleRequest {
    @NotBlank(message = DomainConstants.FIELD_NAME_EMPTY_MESSAGE)
    private String name;

    @NotBlank(message = DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE)
    private String description;

    @NotNull(message = DomainConstants.FIELD_QUANTITY_NULL_MESSAGE)
    @PositiveOrZero(message = DomainConstants.INVALID_QUANTITY_MESSAGE)
    private Integer quantity;

    @NotNull(message = DomainConstants.FIELD_PRICE_NULL_MESSAGE)
    @Positive(message = DomainConstants.INVALID_PRICE_MESSAGE)
    private BigDecimal price;

    @NotNull(message = DomainConstants.FIELD_BRAND_ID_NULL_MESSAGE)
    @Positive(message = DomainConstants.INVALID_BRAND_ID_MESSAGE)
    private Long brandID;

    @Size(min = DomainConstants.MIN_FIELD_CATEGORY_LIST,
            max = DomainConstants.MAX_FIELD_CATEGORY_LIST,
            message = DomainConstants.INVALID_CATEGORY_LIST_MESSAGE
    )
    @UniqueElements(message = DomainConstants.DUPLICATED_CATEGORY_MESSAGE)
    private List<Long> categoryIDs;
}

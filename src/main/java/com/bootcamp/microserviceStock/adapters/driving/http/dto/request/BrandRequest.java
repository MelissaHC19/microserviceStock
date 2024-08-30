package com.bootcamp.microserviceStock.adapters.driving.http.dto.request;

import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BrandRequest {
    @NotBlank(message = DomainConstants.FIELD_NAME_EMPTY_MESSAGE)
    @Size(max = DomainConstants.)
    private final String name;
    private final String description;
}
package com.bootcamp.microserviceStock.adapters.driving.http.dto.request;

import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryRequest {
    @NotBlank(message = DomainConstants.FIELD_NAME_EMPTY_MESSAGE)
    @Size(max = DomainConstants.MAX_FIELD_SIZE_NAME, message = DomainConstants.MAX_FIELD_SIZE_NAME_MESSAGE)
    private final String name;

    @NotBlank(message = DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE)
    @Size(max = DomainConstants.MAX_FIELD_SIZE_DESCRIPTION, message = DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_MESSAGE)
    private final String description;
}

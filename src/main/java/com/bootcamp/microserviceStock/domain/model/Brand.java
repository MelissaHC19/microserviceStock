package com.bootcamp.microserviceStock.domain.model;

import com.bootcamp.microserviceStock.domain.exception.EmptyFieldException;
import com.bootcamp.microserviceStock.domain.exception.MaxFieldSizeException;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;

public class Brand {
    private Long id;
    private String name;
    private String description;

    public Brand(Long id, String name, String description) {
        if (name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.FIELD_NAME_EMPTY_MESSAGE);
        }
        if (description.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        }
        if (name.length() > DomainConstants.MAX_FIELD_SIZE_NAME) {
            throw new MaxFieldSizeException(DomainConstants.MAX_FIELD_SIZE_NAME_MESSAGE);
        }
        if (description.length() > DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_BRAND) {
            throw new MaxFieldSizeException(DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_BRAND_MESSAGE);
        }

        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

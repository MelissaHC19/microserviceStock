package com.bootcamp.microserviceStock.domain.util;

public final class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_NAME_EMPTY_MESSAGE = "Field 'name' cannot be empty.";
    public static final String FIELD_DESCRIPTION_EMPTY_MESSAGE = "Field 'description' cannot be empty.";
    public static final Integer MAX_FIELD_SIZE_NAME = 50;
    public static final Integer MAX_FIELD_SIZE_DESCRIPTION = 90;
    public static final String MAX_FIELD_SIZE_NAME_MESSAGE = "Field 'name' exceeds the maximum allowed character limit of 50 characters.";
    public static final String MAX_FIELD_SIZE_DESCRIPTION_MESSAGE = "Field 'description' exceeds the maximum allowed character limit of 90 characters.";
    public static final Integer MAX_FIELD_SIZE_DESCRIPTION_BRAND = 120;
    public static final String MAX_FIELD_SIZE_DESCRIPTION_BRAND_MESSAGE = "Field 'description' exceeds the maximum allowed character limit of 120 characters.";
}

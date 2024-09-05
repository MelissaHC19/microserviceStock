package com.bootcamp.microserviceStock.domain.util;

public final class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_NAME_EMPTY_MESSAGE = "Field 'name' cannot be empty.";
    public static final String FIELD_DESCRIPTION_EMPTY_MESSAGE = "Field 'description' cannot be empty.";
    public static final int MAX_FIELD_SIZE_NAME = 50;
    public static final int MAX_FIELD_SIZE_DESCRIPTION = 90;
    public static final int MAX_FIELD_SIZE_DESCRIPTION_BRAND = 120;
    public static final String MAX_FIELD_SIZE_NAME_MESSAGE = "Field 'name' exceeds the maximum allowed character limit of 50 characters.";
    public static final String MAX_FIELD_SIZE_DESCRIPTION_MESSAGE = "Field 'description' exceeds the maximum allowed character limit of 90 characters.";
    public static final String MAX_FIELD_SIZE_DESCRIPTION_BRAND_MESSAGE = "Field 'description' exceeds the maximum allowed character limit of 120 characters.";
    public static final String CATEGORY_CREATED_MESSAGE = "Category created successfully.";
    public static final String BRAND_CREATED_MESSAGE = "Brand created successfully.";
    public static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "Category already exists.";
    public static final String BRAND_ALREADY_EXISTS_MESSAGE = "Brand already exists.";
    public static final String INVALID_PAGE_NUMBER_MESSAGE = "The page number must be non-negative.";
    public static final String INVALID_PAGE_SIZE_MESSAGE = "The page size must be greater than zero.";
    public static final String INVALID_SORT_BY_FIELD_MESSAGE = "The sort by field is invalid.";
    public static final String INVALID_SORT_DIRECTION_MESSAGE = "The sort direction must be 'asc' o 'desc'.";
    public static final String VALID_SORT_BY_FIELD = "name";
    public static final String SORT_DIRECTION_ASC = "asc";
    public static final String SORT_DIRECTION_DESC = "desc";
    public static final String PAGE_NUMBER_NULL_MESSAGE = "Page number must be provided.";
    public static final String PAGE_SIZE_NULL_MESSAGE = "Page size must be provided.";
}
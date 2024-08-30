package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.util;

public final class DrivenConstants {
    private DrivenConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "Category already exists.";
    public static final String BRAND_ALREADY_EXISTS_MESSAGE = "Brand already exists.";
}

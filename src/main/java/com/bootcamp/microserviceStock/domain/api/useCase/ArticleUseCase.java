package com.bootcamp.microserviceStock.domain.api.useCase;

import com.bootcamp.microserviceStock.domain.api.IArticleServicePort;
import com.bootcamp.microserviceStock.domain.exception.ValidationException;
import com.bootcamp.microserviceStock.domain.model.Article;
import com.bootcamp.microserviceStock.domain.spi.IArticlePersistencePort;
import com.bootcamp.microserviceStock.domain.spi.IBrandPersistencePort;
import com.bootcamp.microserviceStock.domain.spi.ICategoryPersistencePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArticleUseCase implements IArticleServicePort {
    private final IArticlePersistencePort articlePersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort, ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createArticle(Article article) {
        ArrayList<String> errors = new ArrayList<>();

        if (article.getName().trim().isEmpty()) {
            errors.add(DomainConstants.FIELD_NAME_EMPTY_MESSAGE);
        }
        if (article.getDescription().trim().isEmpty()) {
            errors.add(DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        }
        if (article.getQuantity() == null) {
            errors.add(DomainConstants.FIELD_QUANTITY_NULL_MESSAGE);
        } else if (article.getQuantity() < 0) {
            errors.add(DomainConstants.INVALID_QUANTITY_MESSAGE);
        }
        if (article.getPrice() == null) {
            errors.add(DomainConstants.FIELD_PRICE_NULL_MESSAGE);
        } else if (article.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add(DomainConstants.INVALID_PRICE_MESSAGE);
        }
        if (article.getBrand().getId() == null) {
            errors.add(DomainConstants.FIELD_BRAND_ID_NULL_MESSAGE);
        } else if (article.getBrand().getId() <= 0) {
            errors.add(DomainConstants.INVALID_BRAND_ID_MESSAGE);
        }
        if (article.getCategoryList().isEmpty()){
            errors.add(DomainConstants.MIN_FIELD_SIZE_CATEGORY_LIST_MESSAGE);
        } else if (article.getCategoryList().size() > DomainConstants.MAX_FIELD_SIZE_CATEGORY_LIST) {
            errors.add(DomainConstants.MAX_FIELD_SIZE_CATEGORY_LIST_MESSAGE);
        }

        Set<Long> categoryIDs = new HashSet<>();

        article.getCategoryList().forEach(category -> {
            if (!categoryIDs.add(category.getId())) {
                errors.add(DomainConstants.DUPLICATED_CATEGORY_MESSAGE);
            }
        });

        for (Long categoryID: categoryIDs) {
            if (!categoryPersistencePort.alreadyExistsByID(categoryID)) {
                errors.add(String.format(DomainConstants.CATEGORY_DOES_NOT_EXIST_MESSAGE, categoryID));
            }
        }

        if (!brandPersistencePort.alreadyExistsByID(article.getBrand().getId())) {
            errors.add(String.format(DomainConstants.BRAND_DOES_NOT_EXIST_MESSAGE, article.getBrand().getId()));
        }

        if (articlePersistencePort.alreadyExistsByName(article.getName())) {
            errors.add(DomainConstants.ARTICLE_ALREADY_EXISTS_MESSAGE);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        articlePersistencePort.createArticle(article);
    }
}

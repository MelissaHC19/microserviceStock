package com.bootcamp.microserviceStock.domain.api.useCase;

import com.bootcamp.microserviceStock.domain.exception.ValidationException;
import com.bootcamp.microserviceStock.domain.model.Article;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.spi.IArticlePersistencePort;
import com.bootcamp.microserviceStock.domain.spi.IBrandPersistencePort;
import com.bootcamp.microserviceStock.domain.spi.ICategoryPersistencePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArticleUseCaseTest {
    @Mock
    private IArticlePersistencePort articlePersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    @Test
    @DisplayName("Inserts an article in the DB")
    void createArticle() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(articlePersistencePort.alreadyExistsByName("SmartX Pro 5G Smartphone")).thenReturn(false);
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(true);

        articleUseCase.createArticle(article);

        Mockito.verify(articlePersistencePort, Mockito.times(1)).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }

    @Test
    @DisplayName("Validation exception when name field is empty")
    void createArticleShouldThrowValidationExceptionWhenNameIsEmpty() {
        Article article = new Article(1L, "", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_EMPTY_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }

    @Test
    @DisplayName("Validation exception when description field is empty")
    void createArticleShouldThrowValidationExceptionWhenDescriptionIsEmpty() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }

    @Test
    @DisplayName("Validation exception when quantity field is null")
    void createArticleShouldThrowValidationExceptionWhenQuantityIsNull() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", null,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_QUANTITY_NULL_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }

    @Test
    @DisplayName("Validation exception when quantity field is negative")
    void createArticleShouldThrowValidationExceptionWhenQuantityIsNegative() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", -1,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_QUANTITY_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }

    @Test
    @DisplayName("Validation exception when price field is null")
    void createArticleShouldThrowValidationExceptionWhenPriceIsNull() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                null,
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_PRICE_NULL_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }

    @Test
    @DisplayName("Validation exception when price field is less than or equal to zero")
    void createArticleShouldThrowValidationExceptionWhenPriceIsLessThanOrEqualToZero() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("0"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PRICE_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }

    @Test
    @DisplayName("Validation exception when brand field is null")
    void createArticleShouldThrowValidationExceptionWhenBrandIsNull() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(null, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_BRAND_ID_NULL_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }

    @Test
    @DisplayName("Validation exception when brand field is less than or equal to zero")
    void createArticleShouldThrowValidationExceptionWhenBrandIsLessThanOrEqualToZero() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(0L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_BRAND_ID_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }

    @Test
    @DisplayName("Validation exception when category list field is empty")
    void createArticleShouldThrowValidationExceptionWhenCategoryListIsEmpty() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of()
        );
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.MIN_FIELD_SIZE_CATEGORY_LIST_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
    }

    @Test
    @DisplayName("Validation exception when category list field exceeds max field size (3)")
    void createArticleShouldThrowValidationExceptionWhenCategoryListExceedsMaxFieldSize() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null),
                        new Category(2L, null, null),
                        new Category(3L, null, null),
                        new Category(4L, null, null)
                )
        );
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.MAX_FIELD_SIZE_CATEGORY_LIST_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
    }

    @Test
    @DisplayName("Validation exception when category list field has duplicated categories")
    void createArticleShouldThrowValidationExceptionWhenCategoryListHasDuplicatedCategories() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null),
                        new Category(1L, null, null)
                )
        );
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.DUPLICATED_CATEGORY_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
    }

    @Test
    @DisplayName("Validation exception when category list field has a category that doesn't exist")
    void createArticleShouldThrowValidationExceptionWhenCategoryListHasCategoryThatDoesNotExist() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(String.format(DomainConstants.CATEGORY_DOES_NOT_EXIST_MESSAGE, article.getCategoryList().get(0).getId()));
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }

    @Test
    @DisplayName("Validation exception when brand field has a brand that doesn't exist")
    void createArticleShouldThrowValidationExceptionWhenBrandHasBrandThatDoesNotExist() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(false);
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(String.format(DomainConstants.BRAND_DOES_NOT_EXIST_MESSAGE, article.getBrand().getId()));
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }

    @Test
    @DisplayName("Validation exception when the article already exists")
    void createArticleShouldThrowValidationExceptionWhenArticleAlreadyExists() {
        Article article = new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(articlePersistencePort.alreadyExistsByName("SmartX Pro 5G Smartphone")).thenReturn(true);
        Mockito.when(brandPersistencePort.alreadyExistsByID(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsByID(article.getCategoryList().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, ()-> {
            articleUseCase.createArticle(article);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.ARTICLE_ALREADY_EXISTS_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsByID(article.getCategoryList().get(0).getId());
    }
}
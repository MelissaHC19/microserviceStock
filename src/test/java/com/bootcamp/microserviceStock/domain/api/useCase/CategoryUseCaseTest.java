package com.bootcamp.microserviceStock.domain.api.useCase;

import com.bootcamp.microserviceStock.domain.exception.ValidationException;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.spi.ICategoryPersistencePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    @DisplayName("Inserts a category in the DB")
    void createCategory() {
        Category category = new Category(1L, "Electronics", "Explore the latest gadgets, from smartphones to home entertainment systems.");
        Mockito.when(categoryPersistencePort.alreadyExistsByName("Electronics")).thenReturn(false);
        categoryUseCase.createCategory(category);
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).createCategory(category);
    }

    @Test
    @DisplayName("Validation exception when name field is empty")
    void createCategoryShouldThrowValidationExceptionWhenNameIsEmpty() {
        Category category = new Category(1L, "", "Find top-quality appliances, cookware, and decor to enhance your living space.");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.createCategory(category);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_EMPTY_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Validation exception when description field is empty")
    void createCategoryShouldThrowValidationExceptionWhenDescriptionIsEmpty() {
        Category category = new Category(1L, "Home & Kitchen", "");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.createCategory(category);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Validation exception when name and description fields are empty")
    void createCategoryShouldThrowValidationExceptionWhenNameAndDescriptionAreEmpty() {
        Category category = new Category(1L, "", "");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.createCategory(category);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_EMPTY_MESSAGE, DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Validation exception when name field exceeds maximum field size (50)")
    void createCategoryShouldThrowValidationExceptionWhenNameExceedsMaxFieldSize() {
        Category category = new Category(1L, "Cutting-Edge Electronic Devices and Innovative Home Gadgets Collection", "Explore cutting-edge devices for home and personal use with our latest tech collection.");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.createCategory(category);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.MAX_FIELD_SIZE_NAME_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Validation exception when description field exceeds maximum field size (90)")
    void createCategoryShouldThrowValidationExceptionWhenDescriptionExceedsMaxFieldSize() {
        Category category = new Category(1L, "Fashion", "Explore a vast array of clothing, footwear, and accessories designed for every occasion, ensuring you always look your best, no matter the event or season.");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.createCategory(category);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Validation exception when name and description fields exceeds maximum field size")
    void createCategoryShouldThrowValidationExceptionWhenNameAndDescriptionExceedsMaxFieldSize() {
        Category category = new Category(1L, "Cutting-Edge Electronic Devices and Innovative Home Gadgets Collection", "Discover the latest innovations in technology with our curated collection of cutting-edge devices, perfect for enhancing both your home and personal life.");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.createCategory(category);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.MAX_FIELD_SIZE_NAME_MESSAGE, DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Validation exception when name field is empty and description field exceeds maximum field size (90)")
    void createCategoryShouldThrowValidationExceptionWhenNameIsEmptyAndDescriptionExceedsMaxFieldSize() {
        Category category = new Category(1L, "", "Explore a vast array of clothing, footwear, and accessories designed for every occasion, ensuring you always look your best, no matter the event or season.");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.createCategory(category);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_EMPTY_MESSAGE, DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Validation exception when name field exceed maximum field size (50) and description field is empty")
    void createCategoryShouldThrowValidationExceptionWhenNameExceedsMaxFieldSizeAndDescriptionIsEmpty() {
        Category category = new Category(1L, "Cutting-Edge Electronic Devices and Innovative Home Gadgets Collection", "");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.createCategory(category);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.MAX_FIELD_SIZE_NAME_MESSAGE, DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Validation exception when category already exists in the DB")
    void createCategoryShouldThrowValidationExceptionWhenCategoryAlreadyExists() {
        Category category = new Category(1L, "Electronics", "Explore the latest gadgets, from smartphones to home entertainment systems.");
        Mockito.when(categoryPersistencePort.alreadyExistsByName("Electronics")).thenReturn(true);
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.createCategory(category);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.CATEGORY_ALREADY_EXISTS_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Validation exception when category already exists in the DB and description field is empty")
    void createCategoryShouldThrowValidationExceptionWhenCategoryAlreadyExistsAndDescriptionIsEmpty() {
        Category category = new Category(1L, "Electronics", "");
        Mockito.when(categoryPersistencePort.alreadyExistsByName("Electronics")).thenReturn(true);
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.createCategory(category);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.CATEGORY_ALREADY_EXISTS_MESSAGE, DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Validation exception when category already exists in the DB and description exceeds maximum field size (90)")
    void createCategoryShouldThrowValidationExceptionWhenCategoryAlreadyExistsAndDescriptionExceedsMaxFieldSize() {
        Category category = new Category(1L, "Electronics", "Discover the latest in electronic gadgets, from smartphones and laptops to home entertainment systems and wearable tech. Stay ahead with cutting-edge devices designed for work, play, and everything in between.");
        Mockito.when(categoryPersistencePort.alreadyExistsByName("Electronics")).thenReturn(true);
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.createCategory(category);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.CATEGORY_ALREADY_EXISTS_MESSAGE, DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("List categories correctly")
    void listCategories() {
        Category category = new Category(1L, "Books", "Browse a vast collection of books across all genres and interests.");
        Pagination<Category> pagination = new Pagination<>(List.of(category), 0, 10, 1L);

        Mockito.when(categoryPersistencePort.listCategories(0, 10, "name", "asc")).thenReturn(pagination);

        Pagination<Category> result = categoryUseCase.listCategories(0, 10, "name", "asc");

        assertNotNull(result, "The result shouldn't be null.");
        assertFalse(result.getContent().isEmpty(), "The content shouldn't be empty.");
        assertEquals(1, result.getContent().size(), "The number of categories should be 1.");

        Category returnedCategory = result.getContent().get(0);
        assertEquals(1L, returnedCategory.getId(), "The category ID should be 1L.");
        assertEquals("Books", returnedCategory.getName(), "The category name should be 'Books'.");
        assertEquals("Browse a vast collection of books across all genres and interests.", returnedCategory.getDescription(), "Browse a vast collection of books across all genres and interests.");

        Mockito.verify(categoryPersistencePort, Mockito.times(1)).listCategories(0, 10, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when pageNumber is null")
    void listCategoriesShouldThrowValidationExceptionWhenPageNumberIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.listCategories(null, 3, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_PAGE_NUMBER_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageNumber is a negative number")
    void listCategoriesShouldThrowValidationExceptionWhenPageNumberIsNegative() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.listCategories(-1, 3, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageSize is null")
    void listCategoriesShouldThrowValidationExceptionWhenPageSizeIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.listCategories(0, null, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_PAGE_SIZE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageSize is less than or equal to zero")
    void listCategoriesShouldThrowValidationExceptionWhenPageSizeIsLessThanOrEqualToZero() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.listCategories(0, -1, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when sortBy is null")
    void listCategoriesShouldThrowValidationExceptionWhenSortByIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.listCategories(0, 3, null, "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_BY_FIELD_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when sortBy is different from 'name'")
    void listCategoriesShouldThrowValidationExceptionWhenSortByIsNotName() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.listCategories(0, 3, "description", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_BY_FIELD_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when sortDirection isn't 'asc' or 'desc'")
    void listCategoriesShouldThrowValidationExceptionWhenSortDirectionIsNotAscOrDesc() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.listCategories(0, 3, "name", "order");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_DIRECTION_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageNumber and pageSize are null")
    void listCategoriesShouldThrowValidationExceptionWhenPageNumberAndPageSizeAreNull() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.listCategories(null, null, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_PAGE_NUMBER_NULL_MESSAGE, DomainConstants.FIELD_PAGE_SIZE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageNumber is negative and pageSize is null")
    void listCategoriesShouldThrowValidationExceptionWhenPageNumberIsNegativeAndPageSizeIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.listCategories(-1, null, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE, DomainConstants.FIELD_PAGE_SIZE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageNumber is null and pageSize is less than or equal to zero")
    void listCategoriesShouldThrowValidationExceptionWhenPageNumberIsNullAndPageSizeIsLessThanOrEqualToZero() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.listCategories(null, -1, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_PAGE_NUMBER_NULL_MESSAGE, DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageNumber is negative and pageSize is less than or equal to zero")
    void listCategoriesShouldThrowValidationExceptionWhenPageNumberIsNegativeAndPageSizeIsLessThanOrEqualToZero() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            categoryUseCase.listCategories(-1, 0, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE, DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
    }
}
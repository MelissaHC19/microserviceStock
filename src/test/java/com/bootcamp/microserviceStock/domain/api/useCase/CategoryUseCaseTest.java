package com.bootcamp.microserviceStock.domain.api.useCase;

import com.bootcamp.microserviceStock.domain.exception.ValidationException;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.spi.ICategoryPersistencePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
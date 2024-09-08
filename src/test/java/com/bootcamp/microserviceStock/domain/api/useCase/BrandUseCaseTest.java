package com.bootcamp.microserviceStock.domain.api.useCase;

import com.bootcamp.microserviceStock.domain.exception.ValidationException;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.spi.IBrandPersistencePort;
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
class BrandUseCaseTest {
    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @Test
    @DisplayName("Inserts a brand in the DB")
    void createBrand() {
        Brand brand = new Brand(1L, "TechNova", "Innovative electronics that combine style and functionality, from smartphones to smart home gadgets.");
        Mockito.when(brandPersistencePort.alreadyExistsByName("TechNova")).thenReturn(false);
        brandUseCase.createBrand(brand);
        Mockito.verify(brandPersistencePort, Mockito.times(1)).createBrand(brand);
    }

    @Test
    @DisplayName("Validation exception when name field is empty")
    void createBrandShouldThrowValidationExceptionWhenNameIsEmpty() {
        Brand brand = new Brand(1L, "", "Innovative electronics that combine style and functionality, from smartphones to smart home gadgets.");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.createBrand(brand);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_EMPTY_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Validation exception when description field is empty")
    void createBrandShouldThrowValidationExceptionWhenDescriptionIsEmpty() {
        Brand brand = new Brand(1L, "TechNova", "");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.createBrand(brand);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Validation exception when name and description fields are empty")
    void createBrandShouldThrowValidationExceptionWhenNameAndDescriptionAreEmpty() {
        Brand brand = new Brand(1L, "", "");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.createBrand(brand);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_EMPTY_MESSAGE, DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Validation exception when name field exceeds maximum field size (50)")
    void createBrandShouldThrowValidationExceptionWhenNameExceedsMaxFieldSize() {
        Brand brand = new Brand(1L, "Eco-Conscious Sustainable Home Essentials and Environmentally Friendly Living Solutions", "Offering eco-friendly home products and essentials that promote sustainable and environmentally responsible living.");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.createBrand(brand);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.MAX_FIELD_SIZE_NAME_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Validation exception when description field exceeds maximum field size (120)")
    void createBrandShouldThrowValidationExceptionWhenDescriptionExceedsMaxFieldSize() {
        Brand brand = new Brand(1L, "EcoHaven", "EcoHaven offers a diverse range of eco-friendly home products designed to enhance your sustainable lifestyle. From biodegradable essentials to energy-efficient solutions, we provide everything you need to live responsibly and reduce your environmental impact.");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.createBrand(brand);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_BRAND_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Validation exception when name and description fields exceeds maximum field size")
    void createBrandShouldThrowValidationExceptionWhenNameAndDescriptionExceedsMaxFieldSize() {
        Brand brand = new Brand(1L, "Eco-Conscious Sustainable Home Essentials and Environmentally Friendly Living Solutions","Offering a selection of eco-friendly home products and essentials that inspire sustainable, environmentally responsible living every day.");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.createBrand(brand);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.MAX_FIELD_SIZE_NAME_MESSAGE, DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_BRAND_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Validation exception when name field is empty and description field exceeds maximum field size (120)")
    void createBrandShouldThrowValidationExceptionWhenNameIsEmptyAndDescriptionExceedsMaxFieldSize() {
        Brand brand = new Brand(1L, "","Offering a selection of eco-friendly home products and essentials that inspire sustainable, environmentally responsible living every day.");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.createBrand(brand);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_EMPTY_MESSAGE, DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_BRAND_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Validation exception when name field exceeds maximum field size (50) and description is empty")
    void createBrandShouldThrowValidationExceptionWhenNameExceedsMaxFieldSizeAndDescriptionIsEmpty() {
        Brand brand = new Brand(1L, "Eco-Conscious Sustainable Home Essentials and Environmentally Friendly Living Solutions","");
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.createBrand(brand);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.MAX_FIELD_SIZE_NAME_MESSAGE, DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Validation exception when brand already exists in the DB")
    void createBrandShouldThrowValidationExceptionWhenBrandAlreadyExists() {
        Brand brand = new Brand(1L, "StyleFusion", "Trendy, affordable clothing and accessories that deliver on quality and style.");
        Mockito.when(brandPersistencePort.alreadyExistsByName("StyleFusion")).thenReturn(true);
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.createBrand(brand);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.BRAND_ALREADY_EXISTS_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Validation exception when brand already exists in the DB and description field is empty")
    void createBrandShouldThrowValidationExceptionWhenBrandAlreadyExistsAndDescriptionIsEmpty() {
        Brand brand = new Brand(1L, "StyleFusion", "");
        Mockito.when(brandPersistencePort.alreadyExistsByName("StyleFusion")).thenReturn(true);
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.createBrand(brand);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.BRAND_ALREADY_EXISTS_MESSAGE, DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Validation exception when brand already exists in the DB and description field exceeds maximum field size (120)")
    void createBrandShouldThrowValidationExceptionWhenBrandAlreadyExistsAndDescriptionExceedsMaxFieldSize() {
        Brand brand = new Brand(1L, "StyleFusion", "Where fashion meets affordability. StyleFusion brings you trendy clothing and accessories that don't compromise on quality or price.");
        Mockito.when(brandPersistencePort.alreadyExistsByName("StyleFusion")).thenReturn(true);
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.createBrand(brand);
        });
        assertThat(exception.getErrors()).contains(DomainConstants.BRAND_ALREADY_EXISTS_MESSAGE, DomainConstants.MAX_FIELD_SIZE_DESCRIPTION_BRAND_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("List brands correctly")
    void listBrands() {
        Brand brand = new Brand(1L, "TechNova", "Innovative electronics that combine style and functionality, from smartphones to smart home gadgets.");
        Pagination<Brand> pagination = new Pagination<>(List.of(brand), 0, 10, 1L);

        Mockito.when(brandPersistencePort.listBrands(0, 10, "name", "asc")).thenReturn(pagination);

        Pagination<Brand> result = brandUseCase.listBrands(0, 10, "name", "asc");

        assertNotNull(result, "The result shouldn't be null.");
        assertFalse(result.getContent().isEmpty(), "The content shouldn't be empty.");
        assertEquals(1, result.getContent().size(), "The number of brands should be 1,");

        Brand returnedBrands = result.getContent().get(0);
        assertEquals(1L, returnedBrands.getId(), "The brand ID should be 1L.");
        assertEquals("TechNova", returnedBrands.getName(), "The brand name should be 'TechNova'.");
        assertEquals("Innovative electronics that combine style and functionality, from smartphones to smart home gadgets.", returnedBrands.getDescription(), "The description should be 'Innovative electronics that combine style and functionality, from smartphones to smart home gadgets.'");

        Mockito.verify(brandPersistencePort, Mockito.times(1)).listBrands(0, 10, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when pageNumber is null")
    void listBrandsShouldThrowValidationExceptionWhenPageNumberIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.listBrands(null, 3, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.PAGE_NUMBER_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageNumber is a negative number")
    void listBrandsShouldThrowValidationExceptionWhenPageNumberIsNegative() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.listBrands(-1, 3, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageSize is null")
    void listBrandsShouldThrowValidationExceptionWhenPageSizeIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.listBrands(0, null, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.PAGE_SIZE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageSize is less than or equal to zero")
    void listBrandsShouldThrowValidationExceptionWhenPageSizeIsLessThanOrEqualToZero() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.listBrands(0, -1, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when sortBy is null")
    void listBrandsShouldThrowValidationExceptionWhenSortByIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.listBrands(0, 3, null, "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_BY_FIELD_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when sortBy is different from 'name'")
    void listBrandsShouldThrowValidationExceptionWhenSortByIsNotName() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.listBrands(0, 3, "description", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_BY_FIELD_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when sortDirection isn't 'asc' or 'desc'")
    void listBrandsShouldThrowValidationExceptionWhenSortDirectionIsNotAscOrDesc() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.listBrands(0, 3, "name", "order");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_DIRECTION_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageNumber and pageSize are null")
    void listBrandsShouldThrowValidationExceptionWhenPageNumberAndPageSizeAreNull() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.listBrands(null, null, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.PAGE_NUMBER_NULL_MESSAGE, DomainConstants.PAGE_SIZE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageNumber is negative and pageSize is null")
    void listBrandsShouldThrowValidationExceptionWhenPageNumberIsNegativeAndPageSizeIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.listBrands(-1, null, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE, DomainConstants.PAGE_SIZE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageNumber is null and pageSize is less than or equal to zero")
    void listBrandsShouldThrowValidationExceptionWhenPageNumberIsNullAndPageSizeIsLessThanOrEqualToZero() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.listBrands(null, -1, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.PAGE_NUMBER_NULL_MESSAGE, DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
    }

    @Test
    @DisplayName("Validation exception when pageNumber is negative and pageSize is less than or equal to zero")
    void listBrandsShouldThrowValidationExceptionWhenPageNumberIsNegativeAndPageSizeIsLessThanOrEqualToZero() {
        ValidationException exception = assertThrows(ValidationException.class, ()->{
            brandUseCase.listBrands(-1, 0, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE, DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
    }
}
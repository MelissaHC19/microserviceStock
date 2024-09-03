package com.bootcamp.microserviceStock.domain.api.useCase;

import com.bootcamp.microserviceStock.domain.exception.ValidationException;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.spi.IBrandPersistencePort;
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
}
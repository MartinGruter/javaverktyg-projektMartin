package se.iths.martin.javaverktygprojekt.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.iths.martin.javaverktygprojekt.exceptions.ProductValidationException;
import se.iths.martin.javaverktygprojekt.model.Product;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductValidatorTest {
    ProductValidator productValidator;

    @BeforeEach
    void setUp() {
        productValidator = new ProductValidator();
    }


    @Test
    @DisplayName("should validate name successfully when name is valid")
    void shouldValidateName() {
        assertDoesNotThrow(() -> productValidator.validateName("Product 1"));
    }


    @Test
    @DisplayName("should throw exception when name is null")
    void shouldThrowWhenNameIsNull() {
        assertThrows(ProductValidationException.class,
                () -> productValidator.validateName(null));
    }


    @Test
    @DisplayName("should throw exception when name is empty")
    void shouldThrowWhenNameIsEmpty() {
        assertThrows(ProductValidationException.class,
                () -> productValidator.validateName("   "));
    }


    @Test
    @DisplayName("should validate price successfully when price is valid")
    void shouldValidatePrice() {
        assertDoesNotThrow(() -> productValidator.validatePrice(10.0));
    }


    @Test
    @DisplayName("should throw exception when price is zero or negative")
    void shouldThrowWhenPriceIsZeroOrNegative() {
        assertThrows(ProductValidationException.class,
                () -> productValidator.validatePrice(0.0));
    }


    @Test
    @DisplayName("should validate description successfully when description is valid")
    void shouldValidateDescription() {
        assertDoesNotThrow(() -> productValidator.validateDescription("This is a product description."));
    }


    @Test
    @DisplayName("should throw exception when description is null")
    void shouldThrowWhenDescriptionIsNull() {
        assertThrows(ProductValidationException.class,
                () -> productValidator.validateDescription(null));
    }


    @Test
    @DisplayName("should throw exception when description is empty")
    void shouldThrowWhenDescriptionIsEmpty() {
        assertThrows(ProductValidationException.class,
                () -> productValidator.validateDescription("   "));
    }


    @Test
    @DisplayName("should validate product successfully when product is valid")
    void shouldValidateProduct() {
        Product validProduct = new Product("Product 1", 10.0, "This is a product description.");
        assertDoesNotThrow(() -> productValidator.validate(validProduct));
    }


    @Test
    @DisplayName("should throw exception when product is null")
    void shouldThrowWhenProductIsNull() {
        assertThrows(ProductValidationException.class,
                () -> productValidator.validate(null));
    }


    @Test
    @DisplayName("should throw exception when product has invalid fields")
    void shouldThrowWhenProductHasInvalidFields() {
        Product invalidProduct = new Product("", -5.0, "");

        assertThrows(ProductValidationException.class,
                () -> productValidator.validate(invalidProduct));
    }

}
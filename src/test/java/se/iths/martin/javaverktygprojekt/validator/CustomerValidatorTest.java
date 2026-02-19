package se.iths.martin.javaverktygprojekt.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.martin.javaverktygprojekt.exceptions.CustomerValidationException;
import se.iths.martin.javaverktygprojekt.model.Customer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerValidatorTest {

    private CustomerValidator customerValidator;

    @BeforeEach
    void setup() {
        customerValidator = new CustomerValidator();
    }

    @Test
    void testIfValidateMethodWorks() {
        Customer customer = new Customer(
                "Martin", 28, "martin.test@gmail.com", "Stockholm");

        assertDoesNotThrow(() -> customerValidator.validate(customer));
    }

    @Test
    void throwExceptionIfCustomerNameIsNullTest() {
        assertThrows(CustomerValidationException.class,
                () -> customerValidator.validateCustomerName(null));
    }

    @Test
    void throwExceptionIfCustomerNameIsEmptyTest() {
        assertThrows(CustomerValidationException.class,
                () -> customerValidator.validateCustomerName(""));
    }

    @Test
    void throwExceptionIfAgeIsBelow0() {
        assertThrows(CustomerValidationException.class,
                () -> customerValidator.validateCustomerAge(-1));
    }

    @Test
    void throwExceptionIfAgeIsGreaterThan150() {
        assertThrows(CustomerValidationException.class,
                () -> customerValidator.validateCustomerAge(151));
    }

    @Test
    void throwExceptionIfEmailIsNull() {
        assertThrows(CustomerValidationException.class,
                () -> customerValidator.validateCustomerEmail(null));
    }

    @Test
    void throwExceptionIfEmailIsEmpty() {
        assertThrows(CustomerValidationException.class,
                () -> customerValidator.validateCustomerEmail(""));
    }

    @Test
    void throwExceptionIfCityIsNull() {
        assertThrows(CustomerValidationException.class,
                () -> customerValidator.validateCustomerCity(null));
    }

    @Test
    void throwExceptionIfCityIsEmpty() {
        assertThrows(CustomerValidationException.class,
                () -> customerValidator.validateCustomerCity(""));
    }
}

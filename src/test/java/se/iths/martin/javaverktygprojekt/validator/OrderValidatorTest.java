package se.iths.martin.javaverktygprojekt.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.martin.javaverktygprojekt.exceptions.OrderValidationException;
import se.iths.martin.javaverktygprojekt.model.Order;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderValidatorTest {

    private OrderValidator orderValidator;

    @BeforeEach
    void setUp() {
        orderValidator = new OrderValidator();
    }

    @Test
    void orderIsNullExceptionTest() {
        assertThrows(OrderValidationException.class, () -> orderValidator.validate(null));
    }

    @Test
    void orderNotThrowExceptionTest() {
        Order order = new Order("002", 550, "Bekräftad", LocalDateTime.now());

        assertDoesNotThrow(() -> orderValidator.validate(order));
    }

    @Test
    void orderNumberIsEmptyExceptionTest() {
        assertThrows(OrderValidationException.class, () -> orderValidator.validateOrderNumber(""));
    }

    @Test
    void orderNumberIsNotEmptyExceptionTest() {
        assertDoesNotThrow(() -> orderValidator.validateOrderNumber("123"));
    }

    @Test
    void amountIsZeroExceptionTest() {
        assertThrows(OrderValidationException.class, () -> orderValidator.validateAmount(0));
    }

    @Test
    void amountValidTest() {
        assertDoesNotThrow(() -> orderValidator.validateAmount(550));
    }

    @Test
    void futureOrderDateExceptionTest() {
        LocalDateTime date = LocalDateTime.now().plusDays(1);
        assertThrows(OrderValidationException.class, () -> orderValidator.validateOrderDate(date));
    }

    @Test
    void orderDateNullExceptionTest() {
        assertThrows(OrderValidationException.class, () -> orderValidator.validateOrderDate(null));
    }

    @Test
    void orderStatusEmptyExceptionTest() {
        assertThrows(OrderValidationException.class, () -> orderValidator.validateOrderStatus(""));
    }

    @Test
    void orderStatusNotEmptyExceptionTest() {
        assertDoesNotThrow(() -> orderValidator.validateOrderStatus("Skapad"));
    }
}
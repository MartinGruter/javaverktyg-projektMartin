package se.iths.martin.javaverktygprojekt.validator;

import org.springframework.stereotype.Component;
import se.iths.martin.javaverktygprojekt.exceptions.OrderValidationException;
import se.iths.martin.javaverktygprojekt.model.Order;

import java.time.LocalDateTime;

@Component
public class OrderValidator {

    public void validateOrderNumber(String orderNumber) {
        if (orderNumber == null || orderNumber.isEmpty()) {
            throw new OrderValidationException("Ordernummer kan inte vara tomt");
        }

    }

    public void validateAmount(int amount) {
        if (amount <= 0) {
            throw new OrderValidationException("Beloppet kan inte vara 0 eller mindre");
        }
    }

    public void validateOrderDate(LocalDateTime orderDate) {
        if (orderDate == null) {
            throw new OrderValidationException("Order datum kan inte vara tomt");
        }
        if (orderDate.isAfter(LocalDateTime.now())) {
            throw new OrderValidationException("Du kan inte sätta datumet till framtiden");
        }
    }

    public void validateOrderStatus(String orderStatus) {
        if (orderStatus == null || orderStatus.isEmpty()) {
            throw new OrderValidationException("Orderstatus kan ej vara tomt");
        }
    }

    public void validate(Order order) {
        if (order == null) {
            throw new OrderValidationException("Order kan inte vara tom");
        }

        validateOrderNumber(order.getOrderNumber());
        validateAmount(order.getAmount());
        validateOrderDate(order.getOrderDate());
        validateOrderStatus(order.getOrderStatus());
    }

}

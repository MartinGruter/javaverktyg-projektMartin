package se.iths.martin.javaverktygprojekt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.martin.javaverktygprojekt.exceptions.OrderNotFoundException;
import se.iths.martin.javaverktygprojekt.exceptions.OrderValidationException;
import se.iths.martin.javaverktygprojekt.model.Order;
import se.iths.martin.javaverktygprojekt.repository.OrderRepository;
import se.iths.martin.javaverktygprojekt.validator.OrderValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderValidator orderValidator;

    @Test
    void getAllOrdersTest() {
        List<Order> orders = List.of(
                new Order("123", 500, "Bekräftad", LocalDateTime.now()),
                new Order("002", 1560, "Skickad", LocalDateTime.now().minusWeeks(1))
        );

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(2, result.size());
        verify(orderRepository).findAll();
    }

    @Test
    void getOrderByIdTest() {
        Order order = new Order("001", 250, "Skapad", LocalDateTime.now());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1L);

        assertEquals(order, result);
    }

    @Test
    void getOrderByIdNotFoundTest() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void createOrderTest() {
        Order order = new Order("005", 399, "Bekräftad", LocalDateTime.now());
        when(orderRepository.save(order)).thenReturn(order);
        Order result = orderService.createOrder(order);

        assertEquals(order, result);
        verify(orderRepository).save(order);
    }

    @Test
    void invalidOrderTest() {
        Order order = new Order("", 150, "Skapad", LocalDateTime.now());

        doThrow(new OrderValidationException("Invalid Order")).when(orderValidator).validate(order);

        assertThrows(OrderValidationException.class, () -> orderService.createOrder(order));

        verify(orderRepository, never()).save(order);
    }

    @Test
    void updateOrderTest() {
        Order existingOrder = new Order("001", 250, "Skapad", LocalDateTime.now());
        Order updatedOrder = new Order("001", 300, "Bekräftad", LocalDateTime.now());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);

        Order result = orderService.updateOrder(1L, updatedOrder);

        assertEquals(300, result.getAmount());
        assertEquals("Bekräftad", result.getOrderStatus());

        verify(orderValidator).validateAmount(300);
        verify(orderValidator).validateOrderStatus("Bekräftad");
    }

    @Test
    void invalidUpdateOrderTest() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.updateOrder(1L, new Order("001", 250, "Skapad", LocalDateTime.now())));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void deleteOrderTest() {
        Order order = new Order("001", 250, "Skapad", LocalDateTime.now());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.deleteOrder(1L);

        verify(orderRepository).delete(order);
    }

    @Test
    void deleteOrderNotFoundTest() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(1L));

        verify(orderRepository, never()).delete(any());
    }
}
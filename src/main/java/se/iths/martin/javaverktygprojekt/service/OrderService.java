package se.iths.martin.javaverktygprojekt.service;

import org.springframework.stereotype.Service;
import se.iths.martin.javaverktygprojekt.exceptions.OrderNotFoundException;
import se.iths.martin.javaverktygprojekt.model.Order;
import se.iths.martin.javaverktygprojekt.repository.OrderRepository;
import se.iths.martin.javaverktygprojekt.validator.OrderValidator;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    public OrderService(OrderRepository orderRepository, OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order createOrder(Order order) {
        orderValidator.validate(order);
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        orderValidator.validateAmount(updatedOrder.getAmount());
        orderValidator.validateOrderStatus(updatedOrder.getOrderStatus());
        
        existing.setAmount(updatedOrder.getAmount());
        existing.setOrderStatus(updatedOrder.getOrderStatus());

        return orderRepository.save(existing);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        orderRepository.delete(order);
    }
}

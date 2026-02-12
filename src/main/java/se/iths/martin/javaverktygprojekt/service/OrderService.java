package se.iths.martin.javaverktygprojekt.service;

import org.springframework.stereotype.Service;
import se.iths.martin.javaverktygprojekt.exceptions.OrderNotFoundException;
import se.iths.martin.javaverktygprojekt.model.Order;
import se.iths.martin.javaverktygprojekt.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order exsisting = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        exsisting.setAmount(updatedOrder.getAmount());
        exsisting.setOrderStatus(updatedOrder.getOrderStatus());

        return orderRepository.save(exsisting);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        orderRepository.delete(order);
    }
}

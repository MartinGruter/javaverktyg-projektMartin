package se.iths.martin.javaverktygprojekt.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "total_amount")
    private int amount;

    @Column(name = "status")
    private String orderStatus;

    @Column(name = "created_at")
    private LocalDateTime orderDate;

    public Order() {
    }

    public Order(String orderNumber, int amount, String orderStatus, LocalDateTime orderDate) {
        this.orderNumber = orderNumber;
        this.amount = amount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}

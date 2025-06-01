package org.example.repository;

import org.example.model.Order;
import java.util.List;

public interface IOrderRepository {
    void addOrder(Order order);
    Order getOrderById(int id);
    List<Order> getAllOrders();
    void updateOrder(Order order);
    void deleteOrder(int id);
} 
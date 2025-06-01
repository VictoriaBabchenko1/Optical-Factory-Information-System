package org.example.repository;

import org.example.model.OrderItem;
import java.util.List;

public interface IOrderItemRepository {
    void addOrderItem(OrderItem item);
    OrderItem getOrderItemById(int id);
    List<OrderItem> getOrderItemsByOrderId(int orderId);
    void updateOrderItem(OrderItem item);
    void deleteOrderItem(int id);
} 
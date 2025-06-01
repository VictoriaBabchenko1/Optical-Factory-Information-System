package org.example.service;

import org.example.model.OrderItem;
import java.util.List;

public interface IOrderItemService {
    void addOrderItem(OrderItem item);
    OrderItem getOrderItemById(int id);
    List<OrderItem> getOrderItemsByOrderId(int orderId);
    void updateOrderItem(OrderItem item);
    void deleteOrderItem(int id);
} 
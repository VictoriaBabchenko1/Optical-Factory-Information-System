package org.example.service;

import org.example.model.Order;
import java.util.List;
import java.util.Map;

public interface IOrderService {
    void addOrder(Order order);
    Order getOrderById(int id);
    List<Order> getAllOrders();
    void updateOrder(Order order);
    void deleteOrder(int id);
    List<Map<String, Object>> filterOrders(String clientId, String status, boolean withItems);
    List<Map<String, Object>> getAllClientsForFilter();
    List<String> getAllStatuses();
    List<java.util.Map<String, Object>> filterOrdersAdvanced(String clientId, String status, boolean groupBy, String havingCount, boolean withSum);
} 
package org.example.service;

import org.example.model.Order;
import org.example.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository;

    @Autowired
    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.getOrderById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.updateOrder(order);
    }

    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteOrder(id);
    }

    @Override
    public List<Map<String, Object>> filterOrders(String clientId, String status, boolean withItems) {
        return orderRepository.filterOrders(clientId, status, withItems);
    }

    @Override
    public List<Map<String, Object>> getAllClientsForFilter() {
        return orderRepository.getAllClientsForFilter();
    }

    @Override
    public List<String> getAllStatuses() {
        return orderRepository.getAllStatuses();
    }

    @Override
    public List<java.util.Map<String, Object>> filterOrdersAdvanced(String clientId, String status, boolean groupBy, String havingCount, boolean withSum, boolean qtyGreaterThanAverage) {
        return orderRepository.filterOrdersAdvanced(clientId, status, groupBy, havingCount, withSum, qtyGreaterThanAverage);
    }
} 
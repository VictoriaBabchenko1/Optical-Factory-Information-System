package org.example.service;

import org.example.model.OrderItem;
import org.example.repository.IOrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {
    private final IOrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(IOrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void addOrderItem(OrderItem item) {
        orderItemRepository.addOrderItem(item);
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        return orderItemRepository.getOrderItemById(id);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return orderItemRepository.getOrderItemsByOrderId(orderId);
    }

    @Override
    public void updateOrderItem(OrderItem item) {
        orderItemRepository.updateOrderItem(item);
    }

    @Override
    public void deleteOrderItem(int id) {
        orderItemRepository.deleteOrderItem(id);
    }
} 
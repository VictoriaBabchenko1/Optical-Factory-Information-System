package org.example.controller;

import org.example.model.OrderItem;
import org.example.service.IOrderItemService;
import org.example.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders/{orderId}/items")
public class OrderItemController {
    private final IOrderItemService orderItemService;
    private final IProductService productService;

    @Autowired
    public OrderItemController(IOrderItemService orderItemService, IProductService productService) {
        this.orderItemService = orderItemService;
        this.productService = productService;
    }

    @GetMapping
    public String listOrderItems(@PathVariable int orderId, Model model) {
        List<OrderItem> items = orderItemService.getOrderItemsByOrderId(orderId);
        Map<Integer, String> productNames = productService.getAllProducts().stream().collect(Collectors.toMap(p -> p.getId(), p -> p.getName()));
        model.addAttribute("items", items);
        model.addAttribute("orderId", orderId);
        model.addAttribute("productNames", productNames);
        return "order_items";
    }

    @GetMapping("/add")
    public String showAddForm(@PathVariable int orderId, Model model) {
        OrderItem item = new OrderItem();
        item.setOrderId(orderId);
        model.addAttribute("item", item);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("orderId", orderId);
        return "order_item_form";
    }

    @PostMapping("/add")
    public String addOrderItem(@PathVariable int orderId, @ModelAttribute OrderItem item) {
        item.setOrderId(orderId);
        orderItemService.addOrderItem(item);
        return "redirect:/orders/" + orderId + "/items";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int orderId, @PathVariable int id, Model model) {
        OrderItem item = orderItemService.getOrderItemById(id);
        model.addAttribute("item", item);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("orderId", orderId);
        return "order_item_form";
    }

    @PostMapping("/edit/{id}")
    public String updateOrderItem(@PathVariable int orderId, @PathVariable int id, @ModelAttribute OrderItem item) {
        item.setId(id);
        item.setOrderId(orderId);
        orderItemService.updateOrderItem(item);
        return "redirect:/orders/" + orderId + "/items";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderItem(@PathVariable int orderId, @PathVariable int id) {
        orderItemService.deleteOrderItem(id);
        return "redirect:/orders/" + orderId + "/items";
    }
} 
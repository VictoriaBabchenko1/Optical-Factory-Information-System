package org.example.controller;

import org.example.model.Order;
import org.example.model.Client;
import org.example.service.IOrderService;
import org.example.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final IOrderService orderService;
    private final IClientService clientService;

    @Autowired
    public OrderController(IOrderService orderService, IClientService clientService) {
        this.orderService = orderService;
        this.clientService = clientService;
    }

    @GetMapping
    public String listOrders(Model model,
                            @RequestParam(value = "clientId", required = false) String clientId,
                            @RequestParam(value = "status", required = false) String status,
                            @RequestParam(value = "withItems", required = false) String withItemsParam) {
        boolean withItems = withItemsParam != null;
        List<Map<String, Object>> orders = orderService.filterOrders(clientId, status, withItems);
        model.addAttribute("orders", orders);
        model.addAttribute("clients", orderService.getAllClientsForFilter());
        model.addAttribute("statuses", orderService.getAllStatuses());
        Map<String, String> param = new HashMap<>();
        param.put("clientId", clientId);
        param.put("status", status);
        param.put("withItems", withItems ? "on" : "");
        model.addAttribute("param", param);
        return "orders";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("clients", clientService.getAllClients());
        return "order_form";
    }

    @PostMapping("/add")
    public String addOrder(@ModelAttribute Order order) {
        orderService.addOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("clients", clientService.getAllClients());
        return "order_form";
    }

    @PostMapping("/edit/{id}")
    public String updateOrder(@PathVariable int id, @ModelAttribute Order order) {
        order.setId(id);
        orderService.updateOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
} 
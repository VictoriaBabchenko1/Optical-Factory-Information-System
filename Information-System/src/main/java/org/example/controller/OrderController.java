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
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        List<Client> clients = clientService.getAllClients();
        Map<Integer, String> clientNames = clients.stream().collect(Collectors.toMap(Client::getId, Client::getName));
        model.addAttribute("orders", orders);
        model.addAttribute("clientNames", clientNames);
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
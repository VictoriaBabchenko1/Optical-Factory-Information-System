package org.example.controller;

import org.example.model.Order;
import org.example.model.Client;
import org.example.service.IOrderService;
import org.example.service.IClientService;
import org.example.service.IProductCategoryService;
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
    private final IProductCategoryService productCategoryService;

    @Autowired
    public OrderController(IOrderService orderService, IClientService clientService, IProductCategoryService productCategoryService) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public String listOrders(Model model,
                            @RequestParam(value = "clientId", required = false) String clientId,
                            @RequestParam(value = "status", required = false) String status,
                            @RequestParam(value = "groupBy", required = false) String groupByParam,
                            @RequestParam(value = "havingCount", required = false) String havingCount,
                            @RequestParam(value = "withSum", required = false) String withSumParam,
                            @RequestParam(value = "qtyGreaterThanAverage", required = false) String qtyGreaterThanAverageParam,
                            @RequestParam(value = "minOrdersCount", required = false) String minOrdersCount,
                            @RequestParam(value = "productCategoryId", required = false) String productCategoryId,
                            @RequestParam(value = "includeAnyProductFromCategory", required = false) String includeAnyProductFromCategoryParam) {
        boolean groupBy = groupByParam != null;
        boolean withSum = withSumParam != null;
        boolean qtyGreaterThanAverage = qtyGreaterThanAverageParam != null;
        boolean includeAnyProductFromCategory = includeAnyProductFromCategoryParam != null;
        List<java.util.Map<String, Object>> orders = orderService.filterOrdersAdvanced(clientId, status, groupBy, havingCount, withSum, qtyGreaterThanAverage, minOrdersCount, productCategoryId, includeAnyProductFromCategory);
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("clientId", clientId);
        param.put("status", status);
        param.put("groupBy", groupByParam);
        param.put("havingCount", havingCount);
        param.put("withSum", withSumParam);
        param.put("qtyGreaterThanAverage", qtyGreaterThanAverageParam);
        param.put("minOrdersCount", minOrdersCount);
        param.put("productCategoryId", productCategoryId);
        param.put("includeAnyProductFromCategory", includeAnyProductFromCategoryParam);
        model.addAttribute("param", param);
        model.addAttribute("orders", orders);
        model.addAttribute("clients", orderService.getAllClientsForFilter());
        model.addAttribute("statuses", orderService.getAllStatuses());
        model.addAttribute("productCategories", productCategoryService.getAllCategories());
        model.addAttribute("groupBy", groupBy);
        model.addAttribute("withSum", withSum);
        model.addAttribute("qtyGreaterThanAverage", qtyGreaterThanAverage);
        model.addAttribute("includeAnyProductFromCategory", includeAnyProductFromCategory);
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
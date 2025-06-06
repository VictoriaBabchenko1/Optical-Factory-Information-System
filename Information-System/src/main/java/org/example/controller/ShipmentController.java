package org.example.controller;

import org.example.model.Shipment;
import org.example.service.IShipmentService;
import org.example.service.IOrderService;
import org.example.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shipments")
public class ShipmentController {
    private final IShipmentService shipmentService;
    private final IOrderService orderService;
    private final IEmployeeService employeeService;

    @Autowired
    public ShipmentController(IShipmentService shipmentService, IOrderService orderService, IEmployeeService employeeService) {
        this.shipmentService = shipmentService;
        this.orderService = orderService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listShipments(Model model,
                               @RequestParam(value = "employeeId", required = false) String employeeId,
                               @RequestParam(value = "status", required = false) String status,
                               @RequestParam(value = "groupBy", required = false) String groupByParam,
                               @RequestParam(value = "havingCount", required = false) String havingCount) {
        boolean groupBy = groupByParam != null;
        List<java.util.Map<String, Object>> shipments = shipmentService.filterShipments(employeeId, status, groupBy, havingCount);
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("employeeId", employeeId);
        param.put("status", status);
        param.put("groupBy", groupByParam);
        param.put("havingCount", havingCount);
        model.addAttribute("param", param);
        model.addAttribute("shipments", shipments);
        model.addAttribute("employees", shipmentService.getAllEmployeesForFilter());
        model.addAttribute("statuses", shipmentService.getAllStatuses());
        model.addAttribute("groupBy", groupBy);
        return "shipments";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("shipment", new Shipment());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "shipment_form";
    }

    @PostMapping("/add")
    public String addShipment(@ModelAttribute Shipment shipment) {
        shipmentService.addShipment(shipment);
        return "redirect:/shipments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Shipment shipment = shipmentService.getShipmentById(id);
        model.addAttribute("shipment", shipment);
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "shipment_form";
    }

    @PostMapping("/edit/{id}")
    public String updateShipment(@PathVariable int id, @ModelAttribute Shipment shipment) {
        shipment.setId(id);
        shipmentService.updateShipment(shipment);
        return "redirect:/shipments";
    }

    @GetMapping("/delete/{id}")
    public String deleteShipment(@PathVariable int id) {
        shipmentService.deleteShipment(id);
        return "redirect:/shipments";
    }
} 
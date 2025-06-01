package org.example.controller;

import org.example.model.Warehouse;
import org.example.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/warehouses")
public class WarehouseController {
    private final IWarehouseService warehouseService;

    @Autowired
    public WarehouseController(IWarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public String listWarehouses(Model model) {
        List<Warehouse> warehouses = warehouseService.getAllWarehouses();
        model.addAttribute("warehouses", warehouses);
        return "warehouses";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        return "warehouse_form";
    }

    @PostMapping("/add")
    public String addWarehouse(@ModelAttribute Warehouse warehouse) {
        warehouseService.addWarehouse(warehouse);
        return "redirect:/warehouses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        model.addAttribute("warehouse", warehouse);
        return "warehouse_form";
    }

    @PostMapping("/edit/{id}")
    public String updateWarehouse(@PathVariable int id, @ModelAttribute Warehouse warehouse) {
        warehouse.setId(id);
        warehouseService.updateWarehouse(warehouse);
        return "redirect:/warehouses";
    }

    @GetMapping("/delete/{id}")
    public String deleteWarehouse(@PathVariable int id) {
        warehouseService.deleteWarehouse(id);
        return "redirect:/warehouses";
    }
} 
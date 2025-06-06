package org.example.controller;

import org.example.model.Warehouse;
import org.example.model.Material;
import org.example.service.IWarehouseService;
import org.example.service.IMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/warehouses")
public class WarehouseController {
    private final IWarehouseService warehouseService;
    private final IMaterialService materialService;

    @Autowired
    public WarehouseController(IWarehouseService warehouseService, IMaterialService materialService) {
        this.warehouseService = warehouseService;
        this.materialService = materialService;
    }

    @GetMapping
    public String listWarehouses(
            @RequestParam(value = "groupProducts", defaultValue = "false") boolean groupProducts,
            @RequestParam(value = "materialName", required = false) String materialName,
            @RequestParam(value = "hasProductsWithMaterial", defaultValue = "false") boolean hasProductsWithMaterial,
            Model model) {
        List<Map<String, Object>> warehouses;
        List<Material> materials = materialService.getAllMaterials();

        warehouses = warehouseService.filterWarehouses(groupProducts, materialName, hasProductsWithMaterial);

        model.addAttribute("warehouses", warehouses);
        model.addAttribute("groupProducts", groupProducts);
        model.addAttribute("materials", materials);
        model.addAttribute("materialName", materialName);
        model.addAttribute("hasProductsWithMaterial", hasProductsWithMaterial);
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
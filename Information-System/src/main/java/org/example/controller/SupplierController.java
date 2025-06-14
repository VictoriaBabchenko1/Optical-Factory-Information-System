package org.example.controller;

import org.example.model.Supplier;
import org.example.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    private final ISupplierService supplierService;

    @Autowired
    public SupplierController(ISupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public String listSuppliers(Model model,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "contact", required = false) String contact,
                               @RequestParam(value = "address", required = false) String address,
                               @RequestParam(value = "includeNoProducts", required = false) String includeNoProductsParam) {
        boolean includeNoProducts = includeNoProductsParam != null;
        List<java.util.Map<String, Object>> suppliers = supplierService.filterSuppliers(name, contact, address, includeNoProducts);
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("name", name);
        param.put("contact", contact);
        param.put("address", address);
        param.put("includeNoProducts", includeNoProductsParam);
        model.addAttribute("param", param);
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("includeNoProducts", includeNoProducts);
        return "suppliers";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier_form";
    }

    @PostMapping("/add")
    public String addSupplier(@ModelAttribute Supplier supplier) {
        supplierService.addSupplier(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Supplier supplier = supplierService.getSupplierById(id);
        model.addAttribute("supplier", supplier);
        return "supplier_form";
    }

    @PostMapping("/edit/{id}")
    public String updateSupplier(@PathVariable int id, @ModelAttribute Supplier supplier) {
        supplier.setId(id);
        supplierService.updateSupplier(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable int id) {
        supplierService.deleteSupplier(id);
        return "redirect:/suppliers";
    }
} 
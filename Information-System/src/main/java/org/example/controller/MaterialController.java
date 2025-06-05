package org.example.controller;

import org.example.model.Material;
import org.example.service.IMaterialService;
import org.example.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.model.Supplier;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.List;

@Controller
@RequestMapping("/materials")
public class MaterialController {
    private final IMaterialService materialService;
    private final ISupplierService supplierService;

    @Autowired
    public MaterialController(IMaterialService materialService, ISupplierService supplierService) {
        this.materialService = materialService;
        this.supplierService = supplierService;
    }

    @GetMapping
    public String listMaterials(Model model,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "supplierId", required = false) String supplierId,
                               @RequestParam(value = "priceFrom", required = false) String priceFrom,
                               @RequestParam(value = "priceTo", required = false) String priceTo,
                               @RequestParam(value = "qtyFrom", required = false) String qtyFrom,
                               @RequestParam(value = "qtyTo", required = false) String qtyTo,
                               @RequestParam(value = "inProducts", required = false) String inProductsParam,
                               @RequestParam(value = "sort", required = false) String sort,
                               @RequestParam(value = "showAggregates", required = false) String showAggregatesParam) {
        boolean inProducts = inProductsParam != null;
        boolean showAggregates = showAggregatesParam != null;
        List<java.util.Map<String, Object>> materials = materialService.filterMaterials(name, supplierId, priceFrom, priceTo, qtyFrom, qtyTo, inProducts, sort);
        java.util.Map<String, Object> aggregates = null;
        if (showAggregates) {
            aggregates = materialService.getAggregates(name, supplierId, priceFrom, priceTo, qtyFrom, qtyTo, inProducts);
        }
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("name", name);
        param.put("supplierId", supplierId);
        param.put("priceFrom", priceFrom);
        param.put("priceTo", priceTo);
        param.put("qtyFrom", qtyFrom);
        param.put("qtyTo", qtyTo);
        param.put("inProducts", inProductsParam);
        param.put("sort", sort);
        param.put("showAggregates", showAggregatesParam);
        model.addAttribute("param", param);
        model.addAttribute("materials", materials);
        model.addAttribute("supplierNames", supplierService.getAllSuppliers().stream().collect(Collectors.toMap(org.example.model.Supplier::getId, org.example.model.Supplier::getName)));
        model.addAttribute("aggregates", aggregates);
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "materials";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("material", new Material());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "material_form";
    }

    @PostMapping("/add")
    public String addMaterial(@ModelAttribute Material material) {
        materialService.addMaterial(material);
        return "redirect:/materials";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Material material = materialService.getMaterialById(id);
        model.addAttribute("material", material);
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "material_form";
    }

    @PostMapping("/edit/{id}")
    public String updateMaterial(@PathVariable int id, @ModelAttribute Material material) {
        material.setId(id);
        materialService.updateMaterial(material);
        return "redirect:/materials";
    }

    @GetMapping("/delete/{id}")
    public String deleteMaterial(@PathVariable int id) {
        materialService.deleteMaterial(id);
        return "redirect:/materials";
    }
} 
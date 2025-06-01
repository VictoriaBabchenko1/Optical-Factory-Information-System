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
    public String listMaterials(Model model) {
        List<Material> materials = materialService.getAllMaterials();
        Map<Integer, String> supplierNames = supplierService.getAllSuppliers().stream().collect(Collectors.toMap(Supplier::getId, Supplier::getName));
        model.addAttribute("materials", materials);
        model.addAttribute("supplierNames", supplierNames);
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
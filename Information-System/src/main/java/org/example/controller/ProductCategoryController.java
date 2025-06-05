package org.example.controller;

import org.example.model.ProductCategory;
import org.example.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/categories")
public class ProductCategoryController {
    private final IProductCategoryService categoryService;

    @Autowired
    public ProductCategoryController(IProductCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "withProducts", required = false) String withProductsParam) {
        boolean withProducts = withProductsParam != null;
        List<Map<String, Object>> categories = categoryService.filterCategories(name, withProducts);
        model.addAttribute("categories", categories);
        Map<String, String> param = new HashMap<>();
        param.put("name", name);
        param.put("withProducts", withProducts ? "on" : "");
        model.addAttribute("param", param);
        return "categories";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new ProductCategory());
        return "category_form";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute ProductCategory category) {
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        ProductCategory category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "category_form";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable int id, @ModelAttribute ProductCategory category) {
        category.setId(id);
        categoryService.updateCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
} 
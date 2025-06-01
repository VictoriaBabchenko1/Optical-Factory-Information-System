package org.example.controller;

import org.example.model.Product;
import org.example.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.service.IProductCategoryService;
import org.example.service.IMaterialService;
import org.example.model.ProductCategory;
import org.example.model.Material;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.service.IWarehouseService;
import org.example.model.Warehouse;

@Controller
public class ProductController {
    private final IProductService productService;
    private final IProductCategoryService categoryService;
    private final IMaterialService materialService;
    private final IWarehouseService warehouseService;

    @Autowired
    public ProductController(IProductService productService, IProductCategoryService categoryService, IMaterialService materialService, IWarehouseService warehouseService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.materialService = materialService;
        this.warehouseService = warehouseService;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        Map<Integer, String> categoryNames = categoryService.getAllCategories().stream().collect(Collectors.toMap(ProductCategory::getId, ProductCategory::getName));
        Map<Integer, String> materialNames = materialService.getAllMaterials().stream().collect(Collectors.toMap(Material::getId, Material::getName));
        model.addAttribute("products", products);
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("materialNames", materialNames);
        return "products";
    }

    @GetMapping("/products/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("materials", materialService.getAllMaterials());
        model.addAttribute("warehouses", warehouseService.getAllWarehouses());
        return "product_form";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("materials", materialService.getAllMaterials());
        model.addAttribute("warehouses", warehouseService.getAllWarehouses());
        return "product_form";
    }

    @PostMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute Product product) {
        product.setId(id);
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
} 
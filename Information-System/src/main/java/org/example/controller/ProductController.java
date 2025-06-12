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
import javax.servlet.http.HttpServletRequest;

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
    public String listProducts(
            Model model,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(value = "materialId", required = false) String materialId,
            @RequestParam(value = "priceFrom", required = false) String priceFrom,
            @RequestParam(value = "priceTo", required = false) String priceTo,
            @RequestParam(value = "qtyFrom", required = false) String qtyFrom,
            @RequestParam(value = "qtyTo", required = false) String qtyTo,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "showAggregates", required = false) String showAggregatesParam,
            @RequestParam(value = "uniqueCategories", required = false) String uniqueCategoriesParam,
            @RequestParam(value = "inOrders", required = false) String inOrdersParam,
            @RequestParam(value = "withCategory", required = false) String withCategoryParam,
            @RequestParam(value = "maxPrice", required = false) String maxPriceParam,
            @RequestParam(value = "showUniqueMaterials", required = false) String showUniqueMaterialsParam,
            @RequestParam(value = "priceGreaterThanAnyInCategory", required = false) String priceGreaterThanAnyInCategoryParam,
            @RequestParam(value = "minPrice", required = false) String minPriceParam
    ) {
        boolean showAggregates = showAggregatesParam != null;
        boolean uniqueCategories = uniqueCategoriesParam != null;
        boolean inOrders = inOrdersParam != null;
        boolean withCategory = withCategoryParam != null;
        boolean maxPrice = maxPriceParam != null;
        boolean showUniqueMaterials = showUniqueMaterialsParam != null;
        boolean priceGreaterThanAnyInCategory = priceGreaterThanAnyInCategoryParam != null && categoryId != null && !categoryId.isEmpty();
        boolean minPrice = minPriceParam != null;

        List<Map<String, Object>> products = null;
        List<String> uniqueMaterials = null;

        if (showUniqueMaterials) {
            uniqueMaterials = productService.getUniqueMaterialNames();
        } else {
            products = productService.filterProducts(
                name, categoryId, materialId, priceFrom, priceTo, qtyFrom, qtyTo, sort, uniqueCategories, inOrders, withCategory, maxPrice, minPrice, priceGreaterThanAnyInCategory
            );
        }

        Map<Integer, String> categoryNames = categoryService.getAllCategories().stream().collect(Collectors.toMap(ProductCategory::getId, ProductCategory::getName));
        Map<Integer, String> materialNames = materialService.getAllMaterials().stream().collect(Collectors.toMap(Material::getId, Material::getName));
        model.addAttribute("products", products);
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("materialNames", materialNames);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("materials", materialService.getAllMaterials());
        Map<String, String> param = new java.util.HashMap<>();
        param.put("name", name);
        param.put("categoryId", categoryId);
        param.put("materialId", materialId);
        param.put("priceFrom", priceFrom);
        param.put("priceTo", priceTo);
        param.put("qtyFrom", qtyFrom);
        param.put("qtyTo", qtyTo);
        param.put("sort", sort);
        param.put("showAggregates", showAggregates ? "on" : "");
        param.put("uniqueCategories", uniqueCategories ? "on" : "");
        param.put("inOrders", inOrders ? "on" : "");
        param.put("withCategory", withCategory ? "on" : "");
        param.put("maxPrice", maxPrice ? "on" : "");
        param.put("showUniqueMaterials", showUniqueMaterials ? "on" : "");
        param.put("priceGreaterThanAnyInCategory", priceGreaterThanAnyInCategory ? "on" : "");
        param.put("minPrice", minPrice ? "on" : "");
        model.addAttribute("param", param);
        model.addAttribute("showAggregates", showAggregates);
        model.addAttribute("showUniqueMaterials", showUniqueMaterials);
        if (showAggregates) {
            model.addAttribute("aggregates", productService.getAggregates(
                name, categoryId, materialId, priceFrom, priceTo, qtyFrom, qtyTo, uniqueCategories, inOrders, withCategory, maxPrice
            ));
        }
        if (showUniqueMaterials) {
            model.addAttribute("uniqueMaterials", uniqueMaterials);
        }
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
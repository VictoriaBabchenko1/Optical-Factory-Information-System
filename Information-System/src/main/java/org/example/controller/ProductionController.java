package org.example.controller;

import org.example.model.Production;
import org.example.service.IProductionService;
import org.example.service.IProductService;
import org.example.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@Controller
@RequestMapping("/production")
public class ProductionController {
    private final IProductionService productionService;
    private final IProductService productService;
    private final IEmployeeService employeeService;

    @Autowired
    public ProductionController(IProductionService productionService, IProductService productService, IEmployeeService employeeService) {
        this.productionService = productionService;
        this.productService = productService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listProduction(Model model,
                                 @RequestParam(value = "productId", required = false) String productId,
                                 @RequestParam(value = "employeeId", required = false) String employeeId,
                                 @RequestParam(value = "qtyFrom", required = false) String qtyFrom,
                                 @RequestParam(value = "qtyTo", required = false) String qtyTo,
                                 @RequestParam(value = "sortDate", required = false) String sortDate) {
        List<Map<String, Object>> productions = productionService.filterProduction(productId, employeeId, qtyFrom, qtyTo, sortDate);
        model.addAttribute("productions", productions);
        model.addAttribute("products", productionService.getAllProductsForFilter());
        model.addAttribute("employees", productionService.getAllEmployeesForFilter());
        Map<Integer, String> productsMap = productService.getAllProducts().stream().collect(Collectors.toMap(p -> p.getId(), p -> p.getName()));
        Map<Integer, String> employeesMap = employeeService.getAllEmployees().stream().collect(Collectors.toMap(e -> e.getId(), e -> e.getName()));
        model.addAttribute("productsMap", productsMap);
        model.addAttribute("employeesMap", employeesMap);
        Map<String, String> param = new HashMap<>();
        param.put("productId", productId);
        param.put("employeeId", employeeId);
        param.put("qtyFrom", qtyFrom);
        param.put("qtyTo", qtyTo);
        param.put("sortDate", sortDate);
        model.addAttribute("param", param);
        return "production";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("production", new Production());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "production_form";
    }

    @PostMapping("/add")
    public String addProduction(@ModelAttribute Production production) {
        productionService.addProduction(production);
        return "redirect:/production";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Production production = productionService.getProductionById(id);
        model.addAttribute("production", production);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "production_form";
    }

    @PostMapping("/edit/{id}")
    public String updateProduction(@PathVariable int id, @ModelAttribute Production production) {
        production.setId(id);
        productionService.updateProduction(production);
        return "redirect:/production";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduction(@PathVariable int id) {
        productionService.deleteProduction(id);
        return "redirect:/production";
    }
} 
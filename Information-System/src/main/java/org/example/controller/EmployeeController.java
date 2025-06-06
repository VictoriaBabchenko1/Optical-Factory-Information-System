package org.example.controller;

import org.example.model.Employee;
import org.example.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listEmployees(
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "inShipments", defaultValue = "false") boolean inShipments,
            @RequestParam(value = "inProduction", defaultValue = "false") boolean inProduction,
            @RequestParam(value = "groupProductionCount", defaultValue = "false") boolean groupProductionCount,
            @RequestParam(value = "minProductionCount", required = false) Integer minProductionCount,
            Model model) {
        List<Map<String, Object>> employees = employeeService.filterEmployees(
                searchTerm, inShipments, inProduction, groupProductionCount, minProductionCount
        );
        model.addAttribute("employees", employees);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("inShipments", inShipments);
        model.addAttribute("inProduction", inProduction);
        model.addAttribute("groupProductionCount", groupProductionCount);
        model.addAttribute("minProductionCount", minProductionCount);
        return "employees";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee_form";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.addEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employee_form";
    }

    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable int id, @ModelAttribute Employee employee) {
        employee.setId(id);
        employeeService.updateEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
} 
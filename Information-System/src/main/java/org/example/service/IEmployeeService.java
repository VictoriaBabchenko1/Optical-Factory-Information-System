package org.example.service;

import org.example.model.Employee;
import java.util.List;
import java.util.Map;

public interface IEmployeeService {
    void addEmployee(Employee employee);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployees();
    void updateEmployee(Employee employee);
    void deleteEmployee(int id);
    List<Map<String, Object>> filterEmployees(
            String searchTerm,
            boolean groupProductionCount,
            Integer minProductionCount
    );
} 
package org.example.repository;

import org.example.model.Employee;
import java.util.List;
import java.util.Map;

public interface IEmployeeRepository {
    void addEmployee(Employee employee);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployees();
    void updateEmployee(Employee employee);
    void deleteEmployee(int id);
    List<Map<String, Object>> filterEmployees(
            String searchTerm,
            boolean inShipments,
            boolean inProduction,
            boolean groupProductionCount,
            Integer minProductionCount
    );
}
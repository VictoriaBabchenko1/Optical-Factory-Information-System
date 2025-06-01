package org.example.repository;

import org.example.model.Employee;
import java.util.List;

public interface IEmployeeRepository {
    void addEmployee(Employee employee);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployees();
    void updateEmployee(Employee employee);
    void deleteEmployee(int id);
} 
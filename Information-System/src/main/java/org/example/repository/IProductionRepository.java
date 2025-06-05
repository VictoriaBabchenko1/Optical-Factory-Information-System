package org.example.repository;

import org.example.model.Production;
import java.util.List;
import java.util.Map;

public interface IProductionRepository {
    void addProduction(Production production);
    Production getProductionById(int id);
    List<Production> getAllProductions();
    void updateProduction(Production production);
    void deleteProduction(int id);
    List<Map<String, Object>> filterProduction(String productId, String employeeId, String qtyFrom, String qtyTo, String sortDate);
    List<Map<String, Object>> getAllProductsForFilter();
    List<Map<String, Object>> getAllEmployeesForFilter();
} 
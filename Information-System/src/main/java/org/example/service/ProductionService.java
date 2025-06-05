package org.example.service;

import org.example.model.Production;
import org.example.repository.IProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductionService implements IProductionService {
    private final IProductionRepository productionRepository;

    @Autowired
    public ProductionService(IProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    @Override
    public void addProduction(Production production) {
        productionRepository.addProduction(production);
    }

    @Override
    public Production getProductionById(int id) {
        return productionRepository.getProductionById(id);
    }

    @Override
    public List<Production> getAllProductions() {
        return productionRepository.getAllProductions();
    }

    @Override
    public void updateProduction(Production production) {
        productionRepository.updateProduction(production);
    }

    @Override
    public void deleteProduction(int id) {
        productionRepository.deleteProduction(id);
    }

    @Override
    public List<Map<String, Object>> filterProduction(String productId, String employeeId, String qtyFrom, String qtyTo, String sortDate) {
        return productionRepository.filterProduction(productId, employeeId, qtyFrom, qtyTo, sortDate);
    }

    @Override
    public List<Map<String, Object>> getAllProductsForFilter() {
        return productionRepository.getAllProductsForFilter();
    }

    @Override
    public List<Map<String, Object>> getAllEmployeesForFilter() {
        return productionRepository.getAllEmployeesForFilter();
    }
} 
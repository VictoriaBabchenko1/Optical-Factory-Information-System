package org.example.service;

import org.example.model.Production;
import org.example.repository.IProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
} 
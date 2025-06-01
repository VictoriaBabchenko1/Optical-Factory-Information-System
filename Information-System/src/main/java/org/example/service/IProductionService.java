package org.example.service;

import org.example.model.Production;
import java.util.List;

public interface IProductionService {
    void addProduction(Production production);
    Production getProductionById(int id);
    List<Production> getAllProductions();
    void updateProduction(Production production);
    void deleteProduction(int id);
} 
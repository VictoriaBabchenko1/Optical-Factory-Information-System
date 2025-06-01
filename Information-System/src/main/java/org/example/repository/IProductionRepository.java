package org.example.repository;

import org.example.model.Production;
import java.util.List;

public interface IProductionRepository {
    void addProduction(Production production);
    Production getProductionById(int id);
    List<Production> getAllProductions();
    void updateProduction(Production production);
    void deleteProduction(int id);
} 
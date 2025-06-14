package org.example.repository;

import org.example.model.Material;
import java.util.List;

public interface IMaterialRepository {
    void addMaterial(Material material);
    Material getMaterialById(int id);
    List<Material> getAllMaterials();
    void updateMaterial(Material material);
    void deleteMaterial(int id);
    List<java.util.Map<String, Object>> filterMaterials(String name, String supplierId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, boolean inProducts, String sort);
    java.util.Map<String, Object> getAggregates(String name, String supplierId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, boolean inProducts);
} 
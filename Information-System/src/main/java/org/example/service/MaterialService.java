package org.example.service;

import org.example.model.Material;
import org.example.repository.IMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService implements IMaterialService {
    private final IMaterialRepository materialRepository;

    @Autowired
    public MaterialService(IMaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public void addMaterial(Material material) {
        materialRepository.addMaterial(material);
    }

    @Override
    public Material getMaterialById(int id) {
        return materialRepository.getMaterialById(id);
    }

    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.getAllMaterials();
    }

    @Override
    public void updateMaterial(Material material) {
        materialRepository.updateMaterial(material);
    }

    @Override
    public void deleteMaterial(int id) {
        materialRepository.deleteMaterial(id);
    }

    public List<java.util.Map<String, Object>> filterMaterials(String name, String supplierId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, boolean inProducts, String sort) {
        return materialRepository.filterMaterials(name, supplierId, priceFrom, priceTo, qtyFrom, qtyTo, inProducts, sort);
    }

    public java.util.Map<String, Object> getAggregates(String name, String supplierId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, boolean inProducts) {
        return materialRepository.getAggregates(name, supplierId, priceFrom, priceTo, qtyFrom, qtyTo, inProducts);
    }
} 
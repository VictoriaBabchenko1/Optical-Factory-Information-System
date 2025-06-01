package org.example.service;

import org.example.model.Material;
import java.util.List;

public interface IMaterialService {
    void addMaterial(Material material);
    Material getMaterialById(int id);
    List<Material> getAllMaterials();
    void updateMaterial(Material material);
    void deleteMaterial(int id);
} 
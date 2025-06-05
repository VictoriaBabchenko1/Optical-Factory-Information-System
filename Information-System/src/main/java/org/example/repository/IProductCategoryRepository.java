package org.example.repository;

import org.example.model.ProductCategory;
import java.util.List;
import java.util.Map;

public interface IProductCategoryRepository {
    void addCategory(ProductCategory category);
    ProductCategory getCategoryById(int id);
    List<ProductCategory> getAllCategories();
    void updateCategory(ProductCategory category);
    void deleteCategory(int id);
    List<Map<String, Object>> filterCategories(String name, boolean withProducts);
} 
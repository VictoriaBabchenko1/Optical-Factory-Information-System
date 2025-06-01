package org.example.service;

import org.example.model.ProductCategory;
import java.util.List;

public interface IProductCategoryService {
    void addCategory(ProductCategory category);
    ProductCategory getCategoryById(int id);
    List<ProductCategory> getAllCategories();
    void updateCategory(ProductCategory category);
    void deleteCategory(int id);
} 
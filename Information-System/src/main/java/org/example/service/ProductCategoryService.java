package org.example.service;

import org.example.model.ProductCategory;
import org.example.repository.IProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductCategoryService implements IProductCategoryService {
    private final IProductCategoryRepository categoryRepository;

    @Autowired
    public ProductCategoryService(IProductCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addCategory(ProductCategory category) {
        categoryRepository.addCategory(category);
    }

    @Override
    public ProductCategory getCategoryById(int id) {
        return categoryRepository.getCategoryById(id);
    }

    @Override
    public List<ProductCategory> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public void updateCategory(ProductCategory category) {
        categoryRepository.updateCategory(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteCategory(id);
    }

    @Override
    public List<Map<String, Object>> filterCategories(String name, boolean withProducts) {
        return categoryRepository.filterCategories(name, withProducts);
    }
} 
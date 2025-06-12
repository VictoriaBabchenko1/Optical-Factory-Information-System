package org.example.service;

import org.example.model.Product;
import org.example.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService implements IProductService {
    private final IProductRepository productRepository;

    @Autowired
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
    }

    @Override
    public List<Map<String, Object>> filterProducts(String name, String categoryId, String materialId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, String sort, boolean uniqueCategories, boolean inOrders, boolean withCategory, boolean maxPrice, boolean priceGreaterThanAnyInCategory) {
        return productRepository.filterProducts(name, categoryId, materialId, priceFrom, priceTo, qtyFrom, qtyTo, sort, uniqueCategories, inOrders, withCategory, maxPrice, priceGreaterThanAnyInCategory);
    }

    @Override
    public Map<String, Object> getAggregates(String name, String categoryId, String materialId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, boolean uniqueCategories, boolean inOrders, boolean withCategory, boolean maxPrice) {
        return productRepository.getAggregates(name, categoryId, materialId, priceFrom, priceTo, qtyFrom, qtyTo, uniqueCategories, inOrders, withCategory, maxPrice);
    }

    @Override
    public List<String> getUniqueMaterialNames() {
        return productRepository.getUniqueMaterialNames();
    }
} 
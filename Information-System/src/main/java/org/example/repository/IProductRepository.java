package org.example.repository;

import org.example.model.Product;
import java.util.List;
import java.util.Map;

public interface IProductRepository {
    void addProduct(Product product);
    Product getProductById(int id);
    List<Product> getAllProducts();
    void updateProduct(Product product);
    void deleteProduct(int id);
    List<Map<String, Object>> filterProducts(String name, String categoryId, String materialId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, String sort, boolean uniqueCategories, boolean inOrders, boolean withCategory, boolean maxPrice);
    Map<String, Object> getAggregates(String name, String categoryId, String materialId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, boolean uniqueCategories, boolean inOrders, boolean withCategory, boolean maxPrice);
} 
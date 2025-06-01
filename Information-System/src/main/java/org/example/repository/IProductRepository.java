package org.example.repository;

import org.example.model.Product;
import java.util.List;

public interface IProductRepository {
    void addProduct(Product product);
    Product getProductById(int id);
    List<Product> getAllProducts();
    void updateProduct(Product product);
    void deleteProduct(int id);
} 
package org.example.repository;

import org.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepository implements IProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addProduct(Product product) {
        String sql = "INSERT INTO product (name, category_id, material_id, price, quantity, warehouse_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                product.getName(),
                product.getCategoryId(),
                product.getMaterialId(),
                product.getPrice(),
                product.getQuantity(),
                product.getWarehouseId()
        );
    }

    @Override
    public Product getProductById(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProductRowMapper());
    }

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    @Override
    public void updateProduct(Product product) {
        String sql = "UPDATE product SET name=?, category_id=?, material_id=?, price=?, quantity=?, warehouse_id=? WHERE id=?";
        jdbcTemplate.update(sql,
                product.getName(),
                product.getCategoryId(),
                product.getMaterialId(),
                product.getPrice(),
                product.getQuantity(),
                product.getWarehouseId(),
                product.getId()
        );
    }

    @Override
    public void deleteProduct(int id) {
        String sql = "DELETE FROM product WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setCategoryId(rs.getInt("category_id"));
            product.setMaterialId(rs.getInt("material_id"));
            product.setPrice(rs.getDouble("price"));
            product.setQuantity(rs.getInt("quantity"));
            product.setWarehouseId(rs.getInt("warehouse_id"));
            return product;
        }
    }
} 
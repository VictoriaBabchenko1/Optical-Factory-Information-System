package org.example.repository;

import org.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

    @Override
    public List<Map<String, Object>> filterProducts(String name, String categoryId, String materialId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, String sort, boolean uniqueCategories, boolean inOrders, boolean withCategory, boolean maxPrice) {
        StringBuilder sql = new StringBuilder();
        if (uniqueCategories) {
            sql.append("SELECT DISTINCT category_id FROM product WHERE 1=1");
        } else if (withCategory) {
            sql.append("SELECT p.*, c.name AS category_name FROM product p LEFT JOIN product_category c ON p.category_id = c.id WHERE 1=1");
        } else {
            sql.append("SELECT * FROM product WHERE 1=1");
        }
        List<Object> params = new java.util.ArrayList<>();
        if (name != null && !name.isEmpty()) {
            sql.append(" AND name ILIKE ?");
            params.add("%" + name + "%");
        }
        if (categoryId != null && !categoryId.isEmpty()) {
            sql.append(" AND category_id = ?");
            params.add(Integer.parseInt(categoryId));
        }
        if (materialId != null && !materialId.isEmpty()) {
            sql.append(" AND material_id = ?");
            params.add(Integer.parseInt(materialId));
        }
        if (priceFrom != null && !priceFrom.isEmpty()) {
            sql.append(" AND price >= ?");
            params.add(Double.parseDouble(priceFrom));
        }
        if (priceTo != null && !priceTo.isEmpty()) {
            sql.append(" AND price <= ?");
            params.add(Double.parseDouble(priceTo));
        }
        if (qtyFrom != null && !qtyFrom.isEmpty()) {
            sql.append(" AND quantity >= ?");
            params.add(Integer.parseInt(qtyFrom));
        }
        if (qtyTo != null && !qtyTo.isEmpty()) {
            sql.append(" AND quantity <= ?");
            params.add(Integer.parseInt(qtyTo));
        }
        if (inOrders) {
            sql.append(" AND id IN (SELECT product_id FROM order_item)");
        }
        if (maxPrice) {
            sql.append(" AND price = (SELECT MAX(price) FROM product)");
        }
        if (sort != null && !sort.isEmpty()) {
            sql.append(" ORDER BY ").append(sort);
        }
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    @Override
    public Map<String, Object> getAggregates(String name, String categoryId, String materialId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, boolean uniqueCategories, boolean inOrders, boolean withCategory, boolean maxPrice) {
        StringBuilder sql = new StringBuilder("SELECT MIN(price) AS minPrice, MAX(price) AS maxPrice, AVG(price) AS avgPrice, SUM(price) AS sumPrice, COUNT(*) AS count FROM product WHERE 1=1");
        List<Object> params = new java.util.ArrayList<>();
        if (name != null && !name.isEmpty()) {
            sql.append(" AND name ILIKE ?");
            params.add("%" + name + "%");
        }
        if (categoryId != null && !categoryId.isEmpty()) {
            sql.append(" AND category_id = ?");
            params.add(Integer.parseInt(categoryId));
        }
        if (materialId != null && !materialId.isEmpty()) {
            sql.append(" AND material_id = ?");
            params.add(Integer.parseInt(materialId));
        }
        if (priceFrom != null && !priceFrom.isEmpty()) {
            sql.append(" AND price >= ?");
            params.add(Double.parseDouble(priceFrom));
        }
        if (priceTo != null && !priceTo.isEmpty()) {
            sql.append(" AND price <= ?");
            params.add(Double.parseDouble(priceTo));
        }
        if (qtyFrom != null && !qtyFrom.isEmpty()) {
            sql.append(" AND quantity >= ?");
            params.add(Integer.parseInt(qtyFrom));
        }
        if (qtyTo != null && !qtyTo.isEmpty()) {
            sql.append(" AND quantity <= ?");
            params.add(Integer.parseInt(qtyTo));
        }
        if (inOrders) {
            sql.append(" AND id IN (SELECT product_id FROM order_item)");
        }
        if (maxPrice) {
            sql.append(" AND price = (SELECT MAX(price) FROM product)");
        }
        return jdbcTemplate.queryForMap(sql.toString(), params.toArray());
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
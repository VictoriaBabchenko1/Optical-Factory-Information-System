package org.example.repository;

import org.example.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class ProductCategoryRepository implements IProductCategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addCategory(ProductCategory category) {
        String sql = "INSERT INTO product_category (name, description) VALUES (?, ?)";
        jdbcTemplate.update(sql, category.getName(), category.getDescription());
    }

    @Override
    public ProductCategory getCategoryById(int id) {
        String sql = "SELECT * FROM product_category WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CategoryRowMapper());
    }

    @Override
    public List<ProductCategory> getAllCategories() {
        String sql = "SELECT * FROM product_category";
        return jdbcTemplate.query(sql, new CategoryRowMapper());
    }

    @Override
    public void updateCategory(ProductCategory category) {
        String sql = "UPDATE product_category SET name=?, description=? WHERE id=?";
        jdbcTemplate.update(sql, category.getName(), category.getDescription(), category.getId());
    }

    @Override
    public void deleteCategory(int id) {
        String sql = "DELETE FROM product_category WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Map<String, Object>> filterCategories(String name, boolean withProducts) {
        StringBuilder sql = new StringBuilder();
        if (withProducts) {
            sql.append("SELECT DISTINCT c.id, c.name, c.description FROM product_category c JOIN product p ON c.id = p.category_id WHERE 1=1");
        } else {
            sql.append("SELECT c.id, c.name, c.description FROM product_category c WHERE 1=1");
        }
        List<Object> params = new java.util.ArrayList<>();
        if (name != null && !name.isEmpty()) {
            sql.append(" AND c.name ILIKE ?");
            params.add("%" + name + "%");
        }
        sql.append(" ORDER BY c.name");
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    private static class CategoryRowMapper implements RowMapper<ProductCategory> {
        @Override
        public ProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductCategory category = new ProductCategory();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setDescription(rs.getString("description"));
            return category;
        }
    }
} 
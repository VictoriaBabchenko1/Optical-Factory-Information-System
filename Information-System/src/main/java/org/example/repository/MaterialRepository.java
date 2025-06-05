package org.example.repository;

import org.example.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MaterialRepository implements IMaterialRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MaterialRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addMaterial(Material material) {
        String sql = "INSERT INTO material (name, supplier_id, price, quantity) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, material.getName(), material.getSupplierId(), material.getPrice(), material.getQuantity());
    }

    @Override
    public Material getMaterialById(int id) {
        String sql = "SELECT * FROM material WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new MaterialRowMapper());
    }

    @Override
    public List<Material> getAllMaterials() {
        String sql = "SELECT * FROM material";
        return jdbcTemplate.query(sql, new MaterialRowMapper());
    }

    @Override
    public void updateMaterial(Material material) {
        String sql = "UPDATE material SET name=?, supplier_id=?, price=?, quantity=? WHERE id=?";
        jdbcTemplate.update(sql, material.getName(), material.getSupplierId(), material.getPrice(), material.getQuantity(), material.getId());
    }

    @Override
    public void deleteMaterial(int id) {
        String sql = "DELETE FROM material WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    public List<java.util.Map<String, Object>> filterMaterials(String name, String supplierId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, boolean inProducts, String sort) {
        StringBuilder sql = new StringBuilder("SELECT * FROM material WHERE 1=1");
        List<Object> params = new java.util.ArrayList<>();
        if (name != null && !name.isEmpty()) {
            sql.append(" AND name ILIKE ?");
            params.add("%" + name + "%");
        }
        if (supplierId != null && !supplierId.isEmpty()) {
            sql.append(" AND supplier_id = ?");
            params.add(Integer.parseInt(supplierId));
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
        if (inProducts) {
            sql.append(" AND id IN (SELECT material_id FROM product)");
        }
        if (sort != null && !sort.isEmpty()) {
            sql.append(" ORDER BY " + sort);
        }
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    public java.util.Map<String, Object> getAggregates(String name, String supplierId, String priceFrom, String priceTo, String qtyFrom, String qtyTo, boolean inProducts) {
        StringBuilder sql = new StringBuilder("SELECT MIN(price) AS minPrice, MAX(price) AS maxPrice, AVG(price) AS avgPrice, SUM(price) AS sumPrice, COUNT(*) AS count FROM material WHERE 1=1");
        List<Object> params = new java.util.ArrayList<>();
        if (name != null && !name.isEmpty()) {
            sql.append(" AND name ILIKE ?");
            params.add("%" + name + "%");
        }
        if (supplierId != null && !supplierId.isEmpty()) {
            sql.append(" AND supplier_id = ?");
            params.add(Integer.parseInt(supplierId));
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
        if (inProducts) {
            sql.append(" AND id IN (SELECT material_id FROM product)");
        }
        return jdbcTemplate.queryForMap(sql.toString(), params.toArray());
    }

    private static class MaterialRowMapper implements RowMapper<Material> {
        @Override
        public Material mapRow(ResultSet rs, int rowNum) throws SQLException {
            Material material = new Material();
            material.setId(rs.getInt("id"));
            material.setName(rs.getString("name"));
            material.setSupplierId(rs.getInt("supplier_id"));
            material.setPrice(rs.getDouble("price"));
            material.setQuantity(rs.getInt("quantity"));
            return material;
        }
    }
} 
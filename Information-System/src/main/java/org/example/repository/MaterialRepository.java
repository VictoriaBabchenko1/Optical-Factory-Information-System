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
package org.example.repository;

import org.example.model.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class WarehouseRepository implements IWarehouseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WarehouseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addWarehouse(Warehouse warehouse) {
        String sql = "INSERT INTO warehouse (name, address) VALUES (?, ?)";
        jdbcTemplate.update(sql, warehouse.getName(), warehouse.getAddress());
    }

    @Override
    public Warehouse getWarehouseById(int id) {
        String sql = "SELECT * FROM warehouse WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new WarehouseRowMapper());
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        String sql = "SELECT * FROM warehouse";
        return jdbcTemplate.query(sql, new WarehouseRowMapper());
    }

    @Override
    public List<Map<String, Object>> filterWarehouses(boolean groupProducts, String materialName, boolean hasProductsWithMaterial) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new java.util.ArrayList<>();

        if (groupProducts) {
            sql.append("SELECT w.id, w.name, w.address, COUNT(p.id) AS product_count ");
            sql.append("FROM warehouse w LEFT JOIN product p ON w.id = p.warehouse_id ");
            sql.append("WHERE 1=1 ");
        } else {
            sql.append("SELECT id, name, address FROM warehouse WHERE 1=1 ");
        }

        if (hasProductsWithMaterial && materialName != null && !materialName.isEmpty()) {
            sql.append(" AND id IN (SELECT p.warehouse_id FROM product p JOIN material m ON p.material_id = m.id WHERE m.name = ?)");
            params.add(materialName);
        }

        if (groupProducts) {
            sql.append(" GROUP BY w.id, w.name, w.address");
        }

        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) {
        String sql = "UPDATE warehouse SET name=?, address=? WHERE id=?";
        jdbcTemplate.update(sql, warehouse.getName(), warehouse.getAddress(), warehouse.getId());
    }

    @Override
    public void deleteWarehouse(int id) {
        String sql = "DELETE FROM warehouse WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    private static class WarehouseRowMapper implements RowMapper<Warehouse> {
        @Override
        public Warehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
            Warehouse warehouse = new Warehouse();
            warehouse.setId(rs.getInt("id"));
            warehouse.setName(rs.getString("name"));
            warehouse.setAddress(rs.getString("address"));
            return warehouse;
        }
    }
} 
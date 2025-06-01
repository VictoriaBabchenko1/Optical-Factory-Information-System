package org.example.repository;

import org.example.model.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
package org.example.repository;

import org.example.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ShipmentRepository implements IShipmentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShipmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addShipment(Shipment shipment) {
        String sql = "INSERT INTO shipment (order_id, date, employee_id, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, shipment.getOrderId(), shipment.getDate(), shipment.getEmployeeId(), shipment.getStatus());
    }

    @Override
    public Shipment getShipmentById(int id) {
        String sql = "SELECT * FROM shipment WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ShipmentRowMapper());
    }

    @Override
    public List<Shipment> getAllShipments() {
        String sql = "SELECT * FROM shipment";
        return jdbcTemplate.query(sql, new ShipmentRowMapper());
    }

    @Override
    public void updateShipment(Shipment shipment) {
        String sql = "UPDATE shipment SET order_id=?, date=?, employee_id=?, status=? WHERE id=?";
        jdbcTemplate.update(sql, shipment.getOrderId(), shipment.getDate(), shipment.getEmployeeId(), shipment.getStatus(), shipment.getId());
    }

    @Override
    public void deleteShipment(int id) {
        String sql = "DELETE FROM shipment WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    public List<java.util.Map<String, Object>> filterShipments(String employeeId, String status, boolean groupBy, String havingCount) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new java.util.ArrayList<>();
        if (groupBy) {
            sql.append("SELECT s.employee_id, e.name as employee_name, COUNT(s.id) as shipment_count FROM shipment s LEFT JOIN employee e ON s.employee_id = e.id WHERE 1=1");
        } else {
            sql.append("SELECT s.* FROM shipment s WHERE 1=1");
        }
        if (employeeId != null && !employeeId.isEmpty()) {
            sql.append(" AND s.employee_id = ?");
            params.add(Integer.parseInt(employeeId));
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND s.status = ?");
            params.add(status);
        }
        if (groupBy) {
            sql.append(" GROUP BY s.employee_id, e.name");
            if (havingCount != null && !havingCount.isEmpty()) {
                sql.append(" HAVING COUNT(s.id) >= ?");
                params.add(Integer.parseInt(havingCount));
            }
        }
        sql.append(" ORDER BY s.employee_id");
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    @Override
    public List<java.util.Map<String, Object>> getAllEmployeesForFilter() {
        String sql = "SELECT id, name FROM employee ORDER BY name";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<String> getAllStatuses() {
        String sql = "SELECT DISTINCT status FROM shipment ORDER BY status";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    private static class ShipmentRowMapper implements RowMapper<Shipment> {
        @Override
        public Shipment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Shipment shipment = new Shipment();
            shipment.setId(rs.getInt("id"));
            shipment.setOrderId(rs.getInt("order_id"));
            shipment.setDate(rs.getDate("date"));
            shipment.setEmployeeId(rs.getInt("employee_id"));
            shipment.setStatus(rs.getString("status"));
            return shipment;
        }
    }
} 
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
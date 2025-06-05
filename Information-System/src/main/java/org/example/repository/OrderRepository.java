package org.example.repository;

import org.example.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository implements IOrderRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addOrder(Order order) {
        String sql = "INSERT INTO \"order\" (client_id, date, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, order.getClientId(), order.getDate(), order.getStatus());
    }

    @Override
    public Order getOrderById(int id) {
        String sql = "SELECT * FROM \"order\" WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new OrderRowMapper());
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM \"order\"";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    @Override
    public void updateOrder(Order order) {
        String sql = "UPDATE \"order\" SET client_id=?, date=?, status=? WHERE id=?";
        jdbcTemplate.update(sql, order.getClientId(), order.getDate(), order.getStatus(), order.getId());
    }

    @Override
    public void deleteOrder(int id) {
        String sql = "DELETE FROM \"order\" WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Map<String, Object>> filterOrders(String clientId, String status, boolean withItems) {
        StringBuilder sql = new StringBuilder("SELECT * FROM \"order\" WHERE 1=1");
        List<Object> params = new java.util.ArrayList<>();
        if (clientId != null && !clientId.isEmpty()) {
            sql.append(" AND client_id = ?");
            params.add(Integer.parseInt(clientId));
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        if (withItems) {
            sql.append(" AND EXISTS (SELECT 1 FROM order_item oi WHERE oi.order_id = \"order\".id)");
        }
        sql.append(" ORDER BY date DESC");
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    @Override
    public List<Map<String, Object>> getAllClientsForFilter() {
        String sql = "SELECT id, name FROM client ORDER BY name";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<String> getAllStatuses() {
        String sql = "SELECT DISTINCT status FROM \"order\" ORDER BY status";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    private static class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setClientId(rs.getInt("client_id"));
            order.setDate(rs.getDate("date"));
            order.setStatus(rs.getString("status"));
            return order;
        }
    }
} 
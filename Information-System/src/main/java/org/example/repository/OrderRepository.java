package org.example.repository;

import org.example.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
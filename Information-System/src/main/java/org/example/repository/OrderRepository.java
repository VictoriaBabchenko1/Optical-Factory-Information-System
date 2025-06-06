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

    @Override
    public List<java.util.Map<String, Object>> filterOrdersAdvanced(String clientId, String status, boolean groupBy, String havingCount, boolean withSum, boolean qtyGreaterThanAverage, String minOrdersCount, String productCategoryId, boolean includeAnyProductFromCategory) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new java.util.ArrayList<>();
        if (groupBy) {
            if (withSum) {
                sql.append("SELECT o.client_id, c.name as client_name, COUNT(o.id) as order_count, SUM(oi.price * oi.quantity) as total_sum FROM \"order\" o LEFT JOIN client c ON o.client_id = c.id LEFT JOIN order_item oi ON o.id = oi.order_id WHERE 1=1");
            } else {
                sql.append("SELECT o.client_id, c.name as client_name, COUNT(o.id) as order_count FROM \"order\" o LEFT JOIN client c ON o.client_id = c.id WHERE 1=1");
            }
        } else {
            sql.append("SELECT o.* FROM \"order\" o WHERE 1=1");
        }
        if (clientId != null && !clientId.isEmpty()) {
            sql.append(" AND o.client_id = ?");
            params.add(Integer.parseInt(clientId));
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND o.status = ?");
            params.add(status);
        }
        if (minOrdersCount != null && !minOrdersCount.isEmpty() && !groupBy) {
            sql.append(" AND o.client_id IN (SELECT client_id FROM \"order\" GROUP BY client_id HAVING COUNT(id) >= ?)");
            params.add(Integer.parseInt(minOrdersCount));
        }
        if (qtyGreaterThanAverage) {
            sql.append(" AND o.id IN (SELECT order_id FROM order_item GROUP BY order_id HAVING SUM(quantity) > (SELECT AVG(quantity) FROM order_item))");
        }
        if (productCategoryId != null && !productCategoryId.isEmpty() && includeAnyProductFromCategory) {
            sql.append(" AND o.id IN (SELECT oi.order_id FROM order_item oi JOIN product p ON oi.product_id = p.id WHERE p.category_id = ?)");
            params.add(Integer.parseInt(productCategoryId));
        }
        if (groupBy) {
            sql.append(" GROUP BY o.client_id, c.name");
            if (havingCount != null && !havingCount.isEmpty()) {
                sql.append(" HAVING COUNT(o.id) >= ?");
                params.add(Integer.parseInt(havingCount));
            }
        }
        sql.append(" ORDER BY o.client_id");
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
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
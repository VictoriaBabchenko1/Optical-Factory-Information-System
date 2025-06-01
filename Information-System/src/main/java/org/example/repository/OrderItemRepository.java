package org.example.repository;

import org.example.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderItemRepository implements IOrderItemRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addOrderItem(OrderItem item) {
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, item.getOrderId(), item.getProductId(), item.getQuantity(), item.getPrice());
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        String sql = "SELECT * FROM order_item WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new OrderItemRowMapper());
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        return jdbcTemplate.query(sql, new Object[]{orderId}, new OrderItemRowMapper());
    }

    @Override
    public void updateOrderItem(OrderItem item) {
        String sql = "UPDATE order_item SET product_id=?, quantity=?, price=? WHERE id=?";
        jdbcTemplate.update(sql, item.getProductId(), item.getQuantity(), item.getPrice(), item.getId());
    }

    @Override
    public void deleteOrderItem(int id) {
        String sql = "DELETE FROM order_item WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    private static class OrderItemRowMapper implements RowMapper<OrderItem> {
        @Override
        public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderItem item = new OrderItem();
            item.setId(rs.getInt("id"));
            item.setOrderId(rs.getInt("order_id"));
            item.setProductId(rs.getInt("product_id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPrice(rs.getDouble("price"));
            return item;
        }
    }
} 
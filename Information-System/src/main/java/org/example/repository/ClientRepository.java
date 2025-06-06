package org.example.repository;

import org.example.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientRepository implements IClientRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addClient(Client client) {
        String sql = "INSERT INTO client (name, contact, address) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, client.getName(), client.getContact(), client.getAddress());
    }

    @Override
    public Client getClientById(int id) {
        String sql = "SELECT * FROM client WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ClientRowMapper());
    }

    @Override
    public List<Client> getAllClients() {
        String sql = "SELECT * FROM client";
        return jdbcTemplate.query(sql, new ClientRowMapper());
    }

    @Override
    public void updateClient(Client client) {
        String sql = "UPDATE client SET name=?, contact=?, address=? WHERE id=?";
        jdbcTemplate.update(sql, client.getName(), client.getContact(), client.getAddress(), client.getId());
    }

    @Override
    public void deleteClient(int id) {
        String sql = "DELETE FROM client WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    public List<java.util.Map<String, Object>> filterClients(String name, String contact, String address, boolean withProcessingOrders, boolean groupBy, String havingCount, String sortOrder) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new java.util.ArrayList<>();
        if (groupBy) {
            sql.append("SELECT c.id, c.name, c.contact, c.address, COUNT(o.id) as order_count FROM client c LEFT JOIN \"order\" o ON c.id = o.client_id WHERE 1=1");
        } else {
            sql.append("SELECT c.id, c.name, c.contact, c.address FROM client c WHERE 1=1");
        }
        if (name != null && !name.isEmpty()) {
            sql.append(" AND c.name ILIKE ?");
            params.add("%" + name + "%");
        }
        if (contact != null && !contact.isEmpty()) {
            sql.append(" AND c.contact ILIKE ?");
            params.add("%" + contact + "%");
        }
        if (address != null && !address.isEmpty()) {
            sql.append(" AND c.address ILIKE ?");
            params.add("%" + address + "%");
        }
        if (withProcessingOrders) {
            if (groupBy) {
                sql.append(" AND EXISTS (SELECT 1 FROM \"order\" o2 WHERE o2.client_id = c.id AND o2.status = 'В обробці')");
            } else {
                sql.append(" AND EXISTS (SELECT 1 FROM \"order\" o2 WHERE o2.client_id = c.id AND o2.status = 'В обробці')");
            }
        }
        if (groupBy) {
            sql.append(" GROUP BY c.id, c.name, c.contact, c.address");
            if (havingCount != null && !havingCount.isEmpty()) {
                sql.append(" HAVING COUNT(o.id) >= ?");
                params.add(Integer.parseInt(havingCount));
            }
            if (sortOrder != null && !sortOrder.isEmpty()) {
                sql.append(" ORDER BY order_count ").append(sortOrder);
            }
        } else {
            sql.append(" ORDER BY c.name");
        }
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    private static class ClientRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client client = new Client();
            client.setId(rs.getInt("id"));
            client.setName(rs.getString("name"));
            client.setContact(rs.getString("contact"));
            client.setAddress(rs.getString("address"));
            return client;
        }
    }
} 
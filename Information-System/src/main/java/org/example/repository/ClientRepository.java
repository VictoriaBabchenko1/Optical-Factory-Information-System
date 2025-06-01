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
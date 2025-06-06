package org.example.repository;

import org.example.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SupplierRepository implements ISupplierRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SupplierRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addSupplier(Supplier supplier) {
        String sql = "INSERT INTO supplier (name, contact, address) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, supplier.getName(), supplier.getContact(), supplier.getAddress());
    }

    @Override
    public Supplier getSupplierById(int id) {
        String sql = "SELECT * FROM supplier WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new SupplierRowMapper());
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        String sql = "SELECT * FROM supplier";
        return jdbcTemplate.query(sql, new SupplierRowMapper());
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        String sql = "UPDATE supplier SET name=?, contact=?, address=? WHERE id=?";
        jdbcTemplate.update(sql, supplier.getName(), supplier.getContact(), supplier.getAddress(), supplier.getId());
    }

    @Override
    public void deleteSupplier(int id) {
        String sql = "DELETE FROM supplier WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    public List<java.util.Map<String, Object>> filterSuppliers(String name, String contact, String address, boolean includeNoProducts) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new java.util.ArrayList<>();

        if (includeNoProducts) {
            sql.append("SELECT s.id, s.name, s.contact, s.address, p.id AS product_id, p.name AS product_name ");
            sql.append("FROM product p RIGHT JOIN supplier s ON s.id = p.supplier_id WHERE 1=1");
        } else {
            sql.append("SELECT s.id, s.name, s.contact, s.address FROM supplier s WHERE 1=1");
        }

        if (name != null && !name.isEmpty()) {
            sql.append(" AND s.name ILIKE ?");
            params.add("%" + name + "%");
        }
        if (contact != null && !contact.isEmpty()) {
            sql.append(" AND s.contact ILIKE ?");
            params.add("%" + contact + "%");
        }
        if (address != null && !address.isEmpty()) {
            sql.append(" AND s.address ILIKE ?");
            params.add("%" + address + "%");
        }

        // Add an ORDER BY clause, prioritizing supplier name, then product name if applicable
        if (includeNoProducts) {
            sql.append(" ORDER BY s.name, COALESCE(p.name, '')");
        } else {
            sql.append(" ORDER BY s.name");
        }

        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    private static class SupplierRowMapper implements RowMapper<Supplier> {
        @Override
        public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
            Supplier supplier = new Supplier();
            supplier.setId(rs.getInt("id"));
            supplier.setName(rs.getString("name"));
            supplier.setContact(rs.getString("contact"));
            supplier.setAddress(rs.getString("address"));
            return supplier;
        }
    }
} 
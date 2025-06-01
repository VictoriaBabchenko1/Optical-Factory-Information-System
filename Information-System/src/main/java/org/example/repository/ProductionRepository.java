package org.example.repository;

import org.example.model.Production;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductionRepository implements IProductionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addProduction(Production production) {
        String sql = "INSERT INTO production (product_id, date, employee_id, quantity) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, production.getProductId(), production.getDate(), production.getEmployeeId(), production.getQuantity());
    }

    @Override
    public Production getProductionById(int id) {
        String sql = "SELECT * FROM production WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProductionRowMapper());
    }

    @Override
    public List<Production> getAllProductions() {
        String sql = "SELECT * FROM production";
        return jdbcTemplate.query(sql, new ProductionRowMapper());
    }

    @Override
    public void updateProduction(Production production) {
        String sql = "UPDATE production SET product_id=?, date=?, employee_id=?, quantity=? WHERE id=?";
        jdbcTemplate.update(sql, production.getProductId(), production.getDate(), production.getEmployeeId(), production.getQuantity(), production.getId());
    }

    @Override
    public void deleteProduction(int id) {
        String sql = "DELETE FROM production WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    private static class ProductionRowMapper implements RowMapper<Production> {
        @Override
        public Production mapRow(ResultSet rs, int rowNum) throws SQLException {
            Production production = new Production();
            production.setId(rs.getInt("id"));
            production.setProductId(rs.getInt("product_id"));
            production.setDate(rs.getDate("date"));
            production.setEmployeeId(rs.getInt("employee_id"));
            production.setQuantity(rs.getInt("quantity"));
            return production;
        }
    }
} 
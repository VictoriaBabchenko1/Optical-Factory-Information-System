package org.example.repository;

import org.example.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Repository
public class EmployeeRepository implements IEmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employee (name, position, contact) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, employee.getName(), employee.getPosition(), employee.getContact());
    }

    @Override
    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new EmployeeRowMapper());
    }

    @Override
    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employee";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    @Override
    public List<Map<String, Object>> filterEmployees(
            String searchTerm,
            boolean inShipments,
            boolean inProduction,
            boolean groupProductionCount,
            Integer minProductionCount
    ) {
        StringBuilder sql = new StringBuilder();
        List<Object> args = new ArrayList<>();

        sql.append("SELECT e.id, e.name, e.position, e.contact ");
        if (groupProductionCount) {
            sql.append(", COUNT(p.id) AS production_count ");
        }
        sql.append("FROM employee e ");

        if (groupProductionCount) {
            sql.append("LEFT JOIN production p ON e.id = p.employee_id ");
        }

        sql.append("WHERE 1=1 ");

        if (searchTerm != null && !searchTerm.isEmpty()) {
            sql.append("AND (e.name ILIKE ? OR e.position ILIKE ?) ");
            args.add("%" + searchTerm + "%");
            args.add("%" + searchTerm + "%");
        }

        if (inShipments) {
            sql.append("AND EXISTS (SELECT 1 FROM shipment s WHERE s.employee_id = e.id) ");
        }

        if (inProduction) {
            sql.append("AND EXISTS (SELECT 1 FROM production prod WHERE prod.employee_id = e.id) ");
        }

        if (groupProductionCount) {
            sql.append("GROUP BY e.id, e.name, e.position, e.contact ");
            if (minProductionCount != null) {
                sql.append("HAVING COUNT(p.id) >= ? ");
                args.add(minProductionCount);
            }
        }

        return jdbcTemplate.queryForList(sql.toString(), args.toArray());
    }

    @Override
    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET name=?, position=?, contact=? WHERE id=?";
        jdbcTemplate.update(sql, employee.getName(), employee.getPosition(), employee.getContact(), employee.getId());
    }

    @Override
    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employee WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setPosition(rs.getString("position"));
            employee.setContact(rs.getString("contact"));
            return employee;
        }
    }
} 
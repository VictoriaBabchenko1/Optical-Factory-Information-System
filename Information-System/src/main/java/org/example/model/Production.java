package org.example.model;

import java.util.Date;

public class Production {
    private int id;
    private int productId;
    private Date date;
    private int employeeId;
    private int quantity;

    // Геттери і сеттери
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
} 
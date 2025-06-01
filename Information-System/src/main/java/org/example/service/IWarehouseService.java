package org.example.service;

import org.example.model.Warehouse;
import java.util.List;

public interface IWarehouseService {
    void addWarehouse(Warehouse warehouse);
    Warehouse getWarehouseById(int id);
    List<Warehouse> getAllWarehouses();
    void updateWarehouse(Warehouse warehouse);
    void deleteWarehouse(int id);
} 
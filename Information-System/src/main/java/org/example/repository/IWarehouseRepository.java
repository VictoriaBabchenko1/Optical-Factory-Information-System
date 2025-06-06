package org.example.repository;

import org.example.model.Warehouse;
import java.util.List;
import java.util.Map;

public interface IWarehouseRepository {
    void addWarehouse(Warehouse warehouse);
    Warehouse getWarehouseById(int id);
    List<Warehouse> getAllWarehouses();
    void updateWarehouse(Warehouse warehouse);
    void deleteWarehouse(int id);
    List<Map<String, Object>> filterWarehouses(boolean groupProducts);
} 
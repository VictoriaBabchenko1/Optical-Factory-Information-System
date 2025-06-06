package org.example.service;

import org.example.model.Warehouse;
import org.example.repository.IWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WarehouseService implements IWarehouseService {
    private final IWarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseService(IWarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void addWarehouse(Warehouse warehouse) {
        warehouseRepository.addWarehouse(warehouse);
    }

    @Override
    public Warehouse getWarehouseById(int id) {
        return warehouseRepository.getWarehouseById(id);
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.getAllWarehouses();
    }

    @Override
    public List<Map<String, Object>> filterWarehouses(boolean groupProducts) {
        return warehouseRepository.filterWarehouses(groupProducts);
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) {
        warehouseRepository.updateWarehouse(warehouse);
    }

    @Override
    public void deleteWarehouse(int id) {
        warehouseRepository.deleteWarehouse(id);
    }
} 
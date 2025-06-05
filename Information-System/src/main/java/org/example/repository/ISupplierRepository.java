package org.example.repository;

import org.example.model.Supplier;
import java.util.List;

public interface ISupplierRepository {
    void addSupplier(Supplier supplier);
    Supplier getSupplierById(int id);
    List<Supplier> getAllSuppliers();
    void updateSupplier(Supplier supplier);
    void deleteSupplier(int id);
    List<java.util.Map<String, Object>> filterSuppliers(String name, String contact, String address);
} 
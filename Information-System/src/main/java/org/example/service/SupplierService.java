package org.example.service;

import org.example.model.Supplier;
import org.example.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService implements ISupplierService {
    private final ISupplierRepository supplierRepository;

    @Autowired
    public SupplierService(ISupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void addSupplier(Supplier supplier) {
        supplierRepository.addSupplier(supplier);
    }

    @Override
    public Supplier getSupplierById(int id) {
        return supplierRepository.getSupplierById(id);
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.getAllSuppliers();
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        supplierRepository.updateSupplier(supplier);
    }

    @Override
    public void deleteSupplier(int id) {
        supplierRepository.deleteSupplier(id);
    }

    public List<java.util.Map<String, Object>> filterSuppliers(String name, String contact, String address) {
        return supplierRepository.filterSuppliers(name, contact, address);
    }
} 
package org.example.service;

import org.example.model.Shipment;
import org.example.repository.IShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService implements IShipmentService {
    private final IShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentService(IShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public void addShipment(Shipment shipment) {
        shipmentRepository.addShipment(shipment);
    }

    @Override
    public Shipment getShipmentById(int id) {
        return shipmentRepository.getShipmentById(id);
    }

    @Override
    public List<Shipment> getAllShipments() {
        return shipmentRepository.getAllShipments();
    }

    @Override
    public void updateShipment(Shipment shipment) {
        shipmentRepository.updateShipment(shipment);
    }

    @Override
    public void deleteShipment(int id) {
        shipmentRepository.deleteShipment(id);
    }

    public List<java.util.Map<String, Object>> filterShipments(String employeeId, String status, boolean groupBy, String havingCount) {
        return shipmentRepository.filterShipments(employeeId, status, groupBy, havingCount);
    }

    @Override
    public List<java.util.Map<String, Object>> getAllEmployeesForFilter() {
        return shipmentRepository.getAllEmployeesForFilter();
    }

    @Override
    public List<String> getAllStatuses() {
        return shipmentRepository.getAllStatuses();
    }
} 
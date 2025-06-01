package org.example.repository;

import org.example.model.Shipment;
import java.util.List;

public interface IShipmentRepository {
    void addShipment(Shipment shipment);
    Shipment getShipmentById(int id);
    List<Shipment> getAllShipments();
    void updateShipment(Shipment shipment);
    void deleteShipment(int id);
} 
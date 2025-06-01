package org.example.service;

import org.example.model.Shipment;
import java.util.List;

public interface IShipmentService {
    void addShipment(Shipment shipment);
    Shipment getShipmentById(int id);
    List<Shipment> getAllShipments();
    void updateShipment(Shipment shipment);
    void deleteShipment(int id);
} 
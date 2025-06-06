package org.example.service;

import org.example.model.Client;
import org.example.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {
    private final IClientRepository clientRepository;

    @Autowired
    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void addClient(Client client) {
        clientRepository.addClient(client);
    }

    @Override
    public Client getClientById(int id) {
        return clientRepository.getClientById(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    @Override
    public void updateClient(Client client) {
        clientRepository.updateClient(client);
    }

    @Override
    public void deleteClient(int id) {
        clientRepository.deleteClient(id);
    }

    public List<java.util.Map<String, Object>> filterClients(String name, String contact, String address, boolean withProcessingOrders, boolean groupBy, String havingCount, String sortOrder) {
        return clientRepository.filterClients(name, contact, address, withProcessingOrders, groupBy, havingCount, sortOrder);
    }
} 
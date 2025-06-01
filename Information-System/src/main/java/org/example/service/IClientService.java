package org.example.service;

import org.example.model.Client;
import java.util.List;

public interface IClientService {
    void addClient(Client client);
    Client getClientById(int id);
    List<Client> getAllClients();
    void updateClient(Client client);
    void deleteClient(int id);
} 
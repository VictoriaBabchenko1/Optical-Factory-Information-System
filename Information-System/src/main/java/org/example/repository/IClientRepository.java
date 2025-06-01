package org.example.repository;

import org.example.model.Client;
import java.util.List;

public interface IClientRepository {
    void addClient(Client client);
    Client getClientById(int id);
    List<Client> getAllClients();
    void updateClient(Client client);
    void deleteClient(int id);
} 
package services;

import models.Client;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientService implements BasicCRUD<Client> {
    public static List<Client> listClients;

    public ClientService() {
    }

    @Override
    public Client getById(int id) {
        Client foundClient = new Client();
        for (Client client : listClients) {
            if (client.getId() == id) {
                foundClient = client;
            }
        }
        return foundClient;
    }

    public static Client getByUsername(String username) {
        return listClients.stream()
                .filter(e -> e.getName().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Client> getAll() {
        return listClients;
    }

    @Override
    public void create(Client client) {
        listClients.add(client);
    }

    @Override
    public void update(Client client) {
        for (int i = 0; i < listClients.size(); i++) {
            Client existingClient = listClients.get(i);
            if (existingClient.getId() == client.getId()) {
                listClients.set(i, client);
                break;
            }
        }
    }

    @Override
    public boolean isExist(int clientId) {
        Client client = listClients.stream()
                .filter(e -> Objects.equals(e.getId(), clientId))
                .findFirst()
                .orElse(null);
        return client != null;
    }

    @Override
    public void delete(int clientId) {
        listClients = listClients.stream()
                .filter(e -> !Objects.equals(e.getId(), clientId))
                .collect(Collectors.toList());
    }

    @Override
    public void print() {
        for (Client client : listClients) {
            System.out.println(client.toString());
        }
    }
}

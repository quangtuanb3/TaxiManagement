package services;

import Data.Enum.EPath;
import models.Client;
import models.Ride;
import utils.AppUtils;
import utils.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientService implements BasicCRUD<Client> {
    public static List<Client> listClients;
    private static ClientService instance;


    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    public static Client currentClient;

    static {
        listClients = loadData();

    }

    public ClientService() {
    }

    @Override
    public Client getById(String str) {
        int clientId = AppUtils.getInt(str);
        Client client = listClients.stream().filter(e -> e.getId() == clientId).findFirst().orElse(null);
        if (client == null) {
            System.out.println("Client not found. Please try again!");
            getById(str);
        }
        return client;
    }

    @Override
    public Client getById(int id) {
        return listClients.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }


    public static Client getByEmail(String email) {
        return listClients.stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Client> getAll() {
        return listClients;
    }

    @Override
    public boolean create(Client client) {
        if (listClients.stream().anyMatch(e -> Objects.equals(e.getEmail(), client.getEmail()))
                || DriverService.listDrivers.stream().anyMatch(e -> Objects.equals(e.getEmail(), client.getEmail()))
                || ManagerService.listManagers.stream().anyMatch(e -> Objects.equals(e.getEmail(), client.getEmail()))
        ) {
            System.out.println("Email has been used! Please try again.");
            return false;
        }
        listClients.add(client);
        save();
        return true;
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
                .toList();
    }

    public static List<Client> loadData() {
        return new ArrayList<>((List<Client>) Serializable.deserialize(EPath.CLIENTS.getFilePath()));
    }

    public void print() {

        StringBuilder tableBuilder = new StringBuilder();

        tableBuilder.append("| ID   | Name                 | Email                     | Password      | Phone Number    |\n");
        tableBuilder.append("|------|----------------------|---------------------------|---------------|-----------------|\n");
        for (Client c : listClients) {
            tableBuilder.append(c.toTableRow());
        }
        System.out.println(tableBuilder);
    }

    public static void save() {
        Serializable.serialize(listClients, EPath.CLIENTS.getFilePath());
    }

    public static Client getClientByRideId(int rideID) {
        if (RideService.listRides.size() == 0 || listClients.size() == 0) {
            System.out.println("Not found!");
            return null;
        }
        Ride ride = RideService.listRides.stream().filter(e -> Objects.equals(e.getId(), rideID)).findFirst().orElse(null);
        if (ride != null) {
            return ride.getClient();
        }
        return null;
    }
}

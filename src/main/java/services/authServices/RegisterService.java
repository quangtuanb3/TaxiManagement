package services.authServices;

import models.Client;
import services.ClientService;

public class RegisterService {
    public static boolean register(Client client) {
        if (ClientService.listClients.stream().anyMatch(e -> e.getEmail().equals(client.getEmail()))) {
            return false;
        }
        ClientService.listClients.add(client);
        ClientService.save();
        return true;
    }
}


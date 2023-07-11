package services.authServices;

import models.Client;
import services.ClientService;

public class RegisterService {
    public static boolean register(Client client) {
        return ClientService.getInstance().create(client);
    }
}


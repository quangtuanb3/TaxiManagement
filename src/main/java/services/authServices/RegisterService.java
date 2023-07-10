package services.authServices;

import models.Client;
import services.ClientService;

import static views.LoginView.loginMenu;

public class RegisterService {
    public static void register(Client client) {
        //validate
        // make method return boolean
        //
        ClientService.listClients.add(client);
        ClientService.save();

        loginMenu();
    }
}


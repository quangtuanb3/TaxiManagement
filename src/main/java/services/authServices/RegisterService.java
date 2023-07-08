package services.authServices;

import models.Client;
import services.ClientService;
import utils.AppUtils;

import static views.LoginView.loginMenu;

public class RegisterService {
    public static void register() {
        System.out.println("Register: ");
        String name = AppUtils.getString("Input Name");
        String email = AppUtils.getString("Input Email");
        String password = AppUtils.getString("Input Password");
        String phoneNumber = AppUtils.getString("Input Phone number");
        Client client = new Client(name, email, password, phoneNumber);
        ClientService.listClients.add(client);
        ClientService.save();
        System.out.println("Register successful!");
        loginMenu();
    }
}


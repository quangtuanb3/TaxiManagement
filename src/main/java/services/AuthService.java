package services;

import models.Client;
import utils.AppUtils;

import static views.Client.ClientView.clientMenu;
import static views.Driver.DriverView.driverMenu;
import static views.LoginView.loginMenu;
import static views.Manager.MangerView.managerMenu;

public class AuthService {


    public static void login() {
        System.out.println("Login");
        String email = AppUtils.getString("Input email: ");
        String password = AppUtils.getString("Input password: ");
        if (ManagerService.getByEmail(email) != null && ManagerService.getByEmail(email).
                getPassword().getPasscode().
                equals(AppUtils.hashPassword(password,
                        ManagerService.getByEmail(email)
                                .getPassword().getKey()))) {
            ManagerService.currentManager = ManagerService.getByEmail(email);
            if (DriverService.currentDriver != null) {
                DriverService.currentDriver = null;
            }
            if (ClientService.currentClient != null) {
                ClientService.currentClient = null;
            }
            managerMenu();
        } else if (DriverService.getByEmail(email) != null && DriverService.getByEmail(email).
                getPassword().getPasscode().
                equals(AppUtils.hashPassword(password,
                        DriverService.getByEmail(email)
                                .getPassword().getKey()))) {
            DriverService.currentDriver = DriverService.getByEmail(email);
            if (ClientService.currentClient != null) {
                ClientService.currentClient = null;
            }
            if (ManagerService.currentManager != null) {
                ManagerService.currentManager = null;
            }
            driverMenu();
        } else if (ClientService.getByEmail(email) != null && ClientService.getByEmail(email).
                getPassword().getPasscode().
                equals(AppUtils.hashPassword(password,
                        ClientService.getByEmail(email)
                                .getPassword().getKey()))) {
            ClientService.currentClient = ClientService.getByEmail(email);
            if (ManagerService.currentManager != null) {
                ManagerService.currentManager = null;
            }
            if (DriverService.currentDriver != null) {
                DriverService.currentDriver = null;
            }
            clientMenu();

        } else {
            System.out.println("Invalid account!");
            int choice = AppUtils.getIntWithBound("Press 1 to continue or 0 to back to main menu", 0, 1);
            if (choice == 1) {
                login();
            } else {
                loginMenu();
            }

        }

    }

    public static boolean register(Client client) {
        return ClientService.getInstance().create(client);
    }

}


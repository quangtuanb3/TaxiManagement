package views;

import models.Client;
import services.authServices.LoginService;
import services.authServices.RegisterService;
import utils.AppUtils;
import utils.ListView;

import static utils.ListView.loginMenu;


public class LoginView {
    public static void loginMenu() {
        try {
            ListView.printMenu(loginMenu);
            int choice = AppUtils.getIntWithBound("Input choice", 0, 2);
            if (choice == 0) System.exit(1);
            if (choice == 1) {
                LoginService.login();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void register() {
        System.out.println("Register: ");
        String name = AppUtils.getString("Input Name");
        String email = AppUtils.getString("Input Email");
        String password = AppUtils.getString("Input Password");
        String phoneNumber = AppUtils.getString("Input Phone number");
        Client client = new Client(name, email, password, phoneNumber);

        RegisterService.register(client);
        //check boolean
        loginMenu();
    }
}


package views;

import models.Client;

import services.AuthService;
import utils.AppUtils;
import utils.ListView;

import static utils.ListView.loginMenuList;

public class LoginView {
    public static void loginMenu() {
        try {
            ListView.printMenu(loginMenuList);
            int choice = AppUtils.getIntWithBound("Input choice", 0, 2);
            if (choice == 0) System.exit(1);
            if (choice == 1) {
                AuthService.login();
            } else {
                register();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            loginMenu();
        }
    }
    public static void register() {
        System.out.println("Register: ");
        String name = AppUtils.getString("Input Name");
        String email = AppUtils.getString("Input Email");
        String password = AppUtils.getString("Input Password");
        String phoneNumber = AppUtils.getString("Input Phone number");
        Client client = new Client(name, email, password, phoneNumber);
        if (AuthService.register(client)) {
            System.out.println("Register successful!!");
            loginMenu();
        } else {
            System.out.println("Register error!! Please try again");
            register();
        }
    }
}


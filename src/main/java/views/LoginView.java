package views;

import models.Client;

import services.AuthService;
import utils.AppUtils;
import utils.ListView;
import Data.Enum.EPattern;

import static utils.ListView.loginMenuList;

public class LoginView {
    public static void loginMenu() {
        try {
            ListView.printMenu(loginMenuList);
            int choice = AppUtils.getIntWithBound("Input choice: ", 0, 2);
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
        String name = AppUtils.getStringWithPattern(EPattern.NAME_PATTERN);
        String email = AppUtils.getStringWithPattern(EPattern.EMAIL_PATTERN);
        String password = AppUtils.getStringWithPattern(EPattern.PASSWORD_PATTERN);
        String phoneNumber = AppUtils.getStringWithPattern(EPattern.PHONE_PATTERN);
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


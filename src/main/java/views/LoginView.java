package views;

import services.authServices.RegisterService;
import utils.AppUtils;

import static services.authServices.LoginService.login;


public class LoginView {
    static boolean exit = false;

    public static void loginMenu() {
        try {
            do {
                System.out.println("Welcome to QT taxi");
                System.out.println("1. Login");
                System.out.println("2. Sign up");
                System.out.println("0. Quit");
                int select = AppUtils.getIntWithBound("Input choice", 0, 3);
                switch (select) {
                    case 1:
                        login();
                        break;
                    case 2:
                        System.out.println("1. Create new account");
                        System.out.println("0. Back to main menu");
                        int choice = AppUtils.getIntWithBound("Input choice", 0, 1);
                        switch (choice) {
                            case 1:
                                RegisterService.register();
                                break;
                            case 0:
                                exit = false;
                                break;
                        }
                        break;
                    case 0:
                        exit = true;
                        break;
                }
            }
            while (!exit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


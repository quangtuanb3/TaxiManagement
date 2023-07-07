package views.Manager;

import utils.AppUtils;

import java.io.IOException;
import java.util.Scanner;

import static views.Manager.CarManagerView.carMenu;
import static views.Manager.DriverManagerView.driverMangerMenu;
import static views.LoginView.loginMenu;

public class MangerView {
    static Scanner scanner = new Scanner(System.in);


    public static void managerMenu() throws IOException {
        int choice = -1;
        do {
            System.out.println("Menu");
            System.out.println("1. Cars manger");
            System.out.println("2. Drivers manger");
            System.out.println("3. Clients manger");
            System.out.println("4. Rides manger");
            System.out.println("0. Back to Login menu");
            choice = AppUtils.getIntWithBound("Input choice",0, 4);
            switch (choice) {
                case 1:
                    carMenu();
                    break;
                case 2:
                    driverMangerMenu();
                    break;
                case 3:
                    System.out.println("Upgrading...");
                    break;
                case 4:
                    System.out.println("Upgrading..");
                    break;
                case 0:
                    System.out.println("Back to Login menu");
                    loginMenu();
                    break;
            }
        }
        while (choice != 0);
    }


}

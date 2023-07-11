package views.Manager;

import utils.AppUtils;
import utils.ListView;

import java.io.IOException;
import java.util.Scanner;

import static views.Manager.CarManagerView.carMenu;
import static views.Manager.DriverManagerView.driverMangerMenu;
import static views.LoginView.loginMenu;

public class MangerView {
    static Scanner scanner = new Scanner(System.in);


    public static void managerMenu() {
        ListView.printMenu(ListView.managerMenuList);
//        System.out.println("Menu");
//        System.out.println("1. Cars manger");
//        System.out.println("2. Drivers manger");
//        System.out.println("3. Clients manger");
//        System.out.println("4. Rides manger");
//        System.out.println("0. Back to Login menu");
        int choice = AppUtils.getIntWithBound("Input choice", 0, ListView.managerMenuList.size() - 2);
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
}

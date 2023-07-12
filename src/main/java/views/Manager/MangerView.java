package views.Manager;

import utils.AppUtils;
import utils.ListView;

import java.util.Scanner;

import static views.LoginView.loginMenu;
import static views.Manager.CarManagerView.carMenu;
import static views.Manager.DriverManagerView.driverMangerMenu;
import static views.Manager.RideManagerView.rideManagerMenu;

public class MangerView {
    static Scanner scanner = new Scanner(System.in);


    public static void managerMenu() {
        ListView.printMenu(ListView.managerMenuList);
        int choice = AppUtils.getIntWithBound("Input choice", 0, ListView.managerMenuList.size() - 2);
        switch (choice) {
            case 1:
                carMenu();
                break;
            case 2:
                driverMangerMenu();
                break;
            case 3:
//                ClientMenu
                System.out.println("Upgrading...");
                managerMenu();
                break;
            case 4:
                rideManagerMenu();
                break;
            case 0:
                System.out.println("Back to Login menu");
                loginMenu();
                break;
        }

    }
}

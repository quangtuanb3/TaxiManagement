package views.Manager;

import utils.AppUtils;
import utils.ListView;

import static views.LoginView.loginMenu;
import static views.Manager.CarManagerView.carMenu;
import static views.Manager.ClientManagerView.clientManagerMenu;
import static views.Manager.DriverManagerView.driverMangerMenu;
import static views.Manager.RideManagerView.rideManagerMenu;

public class MangerView {
    public static void managerMenu() {
        ListView.printMenu(ListView.managerMenuList);
        int choice = AppUtils.getIntWithBound("Input choice", 0, ListView.managerMenuList.size() - 2);
        switch (choice) {
            case 1 -> carMenu();
            case 2 -> driverMangerMenu();
            case 3 -> clientManagerMenu();
            case 4 -> rideManagerMenu();
            case 0 -> {
                System.out.println("Back to Login menu");
                loginMenu();
            }
        }
        managerMenu();
    }
}

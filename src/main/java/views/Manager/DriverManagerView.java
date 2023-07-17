package views.Manager;

import services.DriverService;
import utils.AppUtils;
import utils.ListView;

import static views.Manager.MangerView.managerMenu;

public class DriverManagerView {
    public static void driverMangerMenu() {
        ListView.printMenu(ListView.driverManagerMenuList);
        int choice = AppUtils.getIntWithBound("Input choice", 0, 4);
        switch (choice) {
            case 1:
                DriverService.getInstance().print();
                break;
            case 2:
                addDriverUI();
                break;
            case 3:
                blockDriver();
                break;
            case 4:
                updateDriverUI();
            case 0:
                System.out.println("Back to manager menu");
                managerMenu();
                break;
        }
        driverMangerMenu();

    }

    private static void addDriverUI() {
        System.out.println("Upgrading.... Please come back after 2023-07-19 20:00:00");

    }

    private static void getDriverDetail() {
        System.out.println("Upgrading.... Please come back after 2023-07-19 20:00:00");
    }

    private static void blockDriver() {
        System.out.println("Upgrading.... Please come back after 2023-07-19 20:00:00");
    }

    private static void updateDriverUI() {
        System.out.println("Upgrading.... Please come back after 2023-07-19 20:00:00");
    }
}

package views;

import models.Driver;
import services.DriverService;
import utils.AppUtils;

import java.io.IOException;

import static views.CarManagerView.assignCar;
import static views.MangerView.managerMenu;

public class DriverManagerView {
    static DriverService driverService = new DriverService();

    public static void driverMangerMenu() throws IOException {
        int choice;
        do {
            System.out.println("Driver manager menu");
            System.out.println("1. List Drivers");
            System.out.println("2. Add new Driver");
            System.out.println("3. Remove Driver");
            System.out.println("4. Get Driver's detail");
            System.out.println("5. Assign car");
            System.out.println("0. Back to main menu");
            choice = AppUtils.getIntWithBound("Input choice", 0, 5);
            switch (choice) {
                case 1:
                    driverService.print();
                    break;
                case 2:
                    driverService.create(addDriverUi());
                    break;
                case 3:
                    removeDriver();
                    break;
                case 4:
                    getDriverDetail();
                    break;
                case 5:
                    assignCar("Drives > assign car");
                    break;
                case 0:
                    System.out.println("Back to manager menu");
                    managerMenu();
                    break;
            }
        }
        while (choice != 0);
    }

    private static Driver addDriverUi() {
        String name = AppUtils.getString("Input name: ");
        String email = AppUtils.getString("Input email: ");
        String password = AppUtils.getString("Input password: ");
        String phoneNumber = AppUtils.getString("Input phone number: ");
        return new Driver(name, email, password, phoneNumber);
    }

    private static void getDriverDetail() {
        System.out.println("Get Driver's detail");
        int driverId = AppUtils.getInt("Input Car id: ");
        if (!driverService.isExist(driverId)) {
            System.out.printf("Not found %d.\n", driverId);
            getDriverDetail();
        }
        System.out.println(driverService.getById(driverId).toString());
    }

    private static void removeDriver() {
        driverService.print();
        int driverId = AppUtils.getInt("Input drive id to remove: ");
        if (!driverService.isExist(driverId)) {
            System.out.printf("Not found %d.\n", driverId);
            removeDriver();
        }
        driverService.delete(driverId);
        System.out.println("Remove driver successfully!");
    }
}

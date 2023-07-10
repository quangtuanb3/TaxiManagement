package views.Manager;

import DAO.Enum.EAccountStatus;
import models.Driver;
import services.DriverService;
import utils.AppUtils;

import java.io.IOException;

import static views.Manager.MangerView.managerMenu;

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
            System.out.println("5. Update Driver's Information");
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
                    blockDriver();
                    break;
                case 4:
                    getDriverDetail();
                    break;
                case 5:
                    updateDriverUi();
                case 0:
                    System.out.println("Back to manager menu");
                    managerMenu();
                    break;
            }
        }
        while (choice != 0);
    }

    private static void updateDriverUi() {
//        this.id = ++currentId;
//        this.model = model;
//        this.licensePlate = licensePlate;
//        this.seats = seats;
//        this.price = price;
//        this.waitPrice = waitPrice;
//        this.insuranceExpiryDate = insuranceExpiryDate;
//        this.registrationExpiryDate = registrationExpiryDate;
//        this.driver = driver;
//        this.status = status;

    }

    private static Driver addDriverUi() {
        String name = AppUtils.getString("Input name: ");
        String email = AppUtils.getString("Input email: ");
        String password = AppUtils.getString("Input password: ");
        String phoneNumber = AppUtils.getString("Input phone number: ");
        return new Driver(name, email, password, phoneNumber, EAccountStatus.ACTIVE);
    }

    private static void getDriverDetail() {
        System.out.println("Get Driver's detail: ");
        int driverId = AppUtils.getInt("Input Driver id: ");
        if (!driverService.isExist(DriverService.listDrivers, driverId)) {
            System.out.printf("Not found %d.\n", driverId);
            getDriverDetail();
        }
        System.out.println(driverService.getById(driverId).toString());
    }

    private static void blockDriver() {
        driverService.print();
        int driverId = AppUtils.getInt("Input drive id to block: ");
        if (!driverService.isExist(DriverService.listDrivers, driverId)) {
            System.out.printf("Not found %d.\n", driverId);
            blockDriver();
        }
        driverService.getById(driverId).setAccountStatus(EAccountStatus.BLOCKED);
        System.out.println("Remove driver successfully!");
    }
}

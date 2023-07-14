package views.Manager;

import Data.Enum.EAccountStatus;
import models.Driver;
import services.DriverService;
import utils.AppUtils;

import static views.Manager.MangerView.managerMenu;

public class DriverManagerView {
    static DriverService driverService = new DriverService();

    public static void driverMangerMenu() {

        int choice = AppUtils.getIntWithBound("Input choice", 0, 5);
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
                updateDriverUi();
            case 0:
                System.out.println("Back to manager menu");
                managerMenu();
                break;
        }

    }

    private static void updateDriverUi() {

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
        if (!driverService.isExist(driverId)) {
            System.out.printf("Not found %d.\n", driverId);
            getDriverDetail();
        }
        System.out.println(driverService.getById(driverId).toString());
    }

    private static void blockDriver() {
        driverService.print();
        int driverId = AppUtils.getInt("Input drive id to block: ");
        if (!driverService.isExist(driverId)) {
            System.out.printf("Not found %d.\n", driverId);
            blockDriver();
        }
        driverService.getById(driverId).setAccountStatus(EAccountStatus.BLOCKED);
        System.out.println("Remove driver successfully!");
    }
}

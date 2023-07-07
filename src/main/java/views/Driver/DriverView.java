package views.Driver;

import services.DriverService;
import services.RideService;
import utils.AppUtils;

import java.io.IOException;

import static views.LoginView.loginMenu;

public class DriverView {
    static RideService rideService = new RideService();

    public static void driverMenu() throws IOException {
        int choice;
        do {
            System.out.println("Driver menu");
            System.out.println("1. Get car detail");
            System.out.println("2. Get ride detail");
            System.out.println("3. Get ride history");
            System.out.println("4. Approve ride");
            System.out.println("5. Decline ride");
            System.out.println("0. Back to login menu");
            choice = AppUtils.getIntWithBound("Input choice", 0, 5);
            switch (choice) {
                case 1:
                    System.out.println("Upgrading...");
                    break;
                case 2:
                    System.out.println("Upgrading...");
                    break;
                case 3:
                    System.out.println("Upgrading...");
                    break;
                case 4:
                    ApproveRideUi();
                    break;
                case 5:
                    System.out.println("Upgrading...");
                    break;
                case 0:
                    System.out.println("Back to Login menu");
                    loginMenu();
                    break;
            }
        }
        while (choice != 0);
    }

    private static void ApproveRideUi() {
        try {
            RideService.printAvailableRides();
            if (!DriverService.currentDriver.isAvailable()) {
                int choice;
                do {
                    System.out.println("You are in unavailable status. Please come back late!");
                    choice = AppUtils.getInt("Press 0 to back to preview menu!");
                    if (choice == 0) driverMenu();
                } while (choice != 0);
            }
            int rideId = AppUtils.getInt("Input ride id: ");
            if (rideService.getById(rideId) != null) {
                rideService.approve(rideId);
            } else {
                System.out.println("Ride Id not found. Please input again: ");
                ApproveRideUi();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

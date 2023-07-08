package views.Driver;

import models.Location;
import services.DriverService;
import services.RideService;
import utils.AppUtils;

import java.io.IOException;
import java.util.stream.Collectors;

import static models.Ride.printRide;
import static services.RideService.waitingRides;
import static utils.AppUtils.getDateTimeNow;
import static utils.AppUtils.getDuration;
import static views.LoginView.loginMenu;

public class DriverView {
    static RideService rideService = new RideService();

    public static void driverMenu() throws IOException {
        int choice;
        do {
            System.out.println("Driver menu");
            System.out.println("1. Approve ride");
            System.out.println("2. Start ride");
            System.out.println("3. Finish ride");
            System.out.println("4. Get car detail");
            System.out.println("5. Get ride detail");
            System.out.println("6. Get ride history");
            System.out.println("0. Back to login menu");
            choice = AppUtils.getIntWithBound("Input choice", 0, 5);
            switch (choice) {
                case 1:
                    ApproveRideUi();
                    break;
                case 2:
                    DriverService.currentDriver.getCurrentRide().setStartTime(getDateTimeNow());
                    break;
                case 3:
                    finishRideUI();
                    break;
                case 4:
                    DriverService.currentDriver.getCar().printDetail();
                    break;
                case 5:
                    System.out.println();
                    printRide(DriverService.currentDriver.getCurrentRide());
                    break;
                case 6:
                    DriverService.printRideHistory();
                    break;

                case 0:
                    System.out.println("Back to Login menu");
                    loginMenu();
                    break;
            }
        }
        while (choice != 0);
    }

    private static void finishRideUI() {
        Location actualDestination = new Location(AppUtils.getString("Input Actual destination: "));
        int waitTime = AppUtils.getIntWithBound("Input time wait (minus): ", 0, 2000);
        rideService.finishRide(actualDestination, waitTime);
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
            if (waitingRides.stream().filter(e -> getDuration(getDateTimeNow(),
                    e.getExpectedPickupTime()) < 15).collect(Collectors.toList())
                    .stream().anyMatch(ride -> ride.getId() == rideId)) {
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

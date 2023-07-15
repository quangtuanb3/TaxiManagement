package views.Driver;

import models.Location;
import services.DriverService;
import services.RideService;
import utils.AppUtils;
import utils.Constant;
import utils.ListView;
import utils.MapQuest;

import java.util.Collections;

import static utils.AppUtils.getDateTimeNow;
import static views.LoginView.loginMenu;

public class DriverView {

    public static void driverMenu() {
        int choice;
        ListView.printMenu(ListView.driverMenuList);
        choice = AppUtils.getIntWithBound("Input choice", 0,
                ListView.driverMenuList.size() - 2);
        switch (choice) {
            case 1 -> {
//                "1. Approve ride"
                System.out.println(ListView.driverMenuList.get(1));
                RideService.autoDeclineRide();
                ApproveRideUi();
            }
            case 2 -> {
//                "2. Start ride"
                System.out.println(ListView.driverMenuList.get(2));
                startRideUI();
            }
            case 3 -> {
//                "3. Finish ride"
                System.out.println(ListView.driverMenuList.get(3));
                finishRideUI();
            }
            case 4 -> {
//                "4. Get car detail"
                System.out.println(ListView.driverMenuList.get(4));
                DriverService.currentDriver.getCar().printDetail();
            }
            case 5 -> {
//                "5. Get ride detail"
                System.out.println(ListView.driverMenuList.get(5));
                RideService.printListRides(Collections.singletonList(RideService.currentRide));
            }
            case 6 -> {
//                "6. Get ride history"
                System.out.println(ListView.driverMenuList.get(6));
                DriverService.printRideHistory();
            }
            case 0 -> {
                // 0 Back to login menu
                System.out.println(ListView.driverMenuList.get(7));
                loginMenu();
            }
        }
        driverMenu();
    }

    private static void startRideUI() {
        if (DriverService.currentDriver == null || DriverService.currentDriver.getCurrentRide() == null) {
            System.out.println("There is no ride");
            driverMenu();
        } else {
            DriverService.currentDriver.getCurrentRide().setStartTime(getDateTimeNow());
            System.out.printf("Welcome %s. Your trip starts at %s. Have a nice trip!\n", DriverService.currentDriver.getCurrentRide().getClient().getName(), getDateTimeNow().format(Constant.DATE_TIME_FORMATTER));
        }

    }

    private static void finishRideUI() {
        if (DriverService.currentDriver == null || DriverService.currentDriver.getCurrentRide() == null) {
            System.out.println("There is no ride");
            driverMenu();
        } else {
            Location actualDestination = new Location(MapQuest.getAddress("Input actual destination: "));
            int waitTime = AppUtils.getIntWithBound("Input actual time wait (minus): ", 0, 2000);
            RideService.getInstance().finishRide(actualDestination, waitTime);
        }
    }

    private static void ApproveRideUi() {
        if (!DriverService.currentDriver.isAvailable()) {
            int choice;
            do {
                System.out.println("You are in unavailable status. Please come back late!");
                choice = AppUtils.getInt("Press 0 to back to preview menu!");
                if (choice == 0) driverMenu();
            } while (choice != 0);
        }
        if (!RideService.printAvailableRides()) {
            driverMenu();
        }
        int rideId = AppUtils.getInt("Input ride id: ");
        if (RideService.getInstance().isInList(RideService.getAvailableRides(), rideId)) {
            if (RideService.getInstance().approve(rideId)) {
                System.out.println("Approve ride successfully!");
            }
        } else {
            System.out.println("Ride not found. Please input again: ");
            ApproveRideUi();
        }
    }
}

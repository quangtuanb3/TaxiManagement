package views.Manager;

import Data.Enum.ERideStatus;
import services.RideService;
import utils.AppUtils;
import utils.ListView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class RideManagerView {
    public static void rideManagerMenu() {
        List<String> rideManagerMenuList = Arrays.asList(
                "Ride Manager Menu",
                "1. Print rides",
                "2. Get all rides from date to date",
                "3. Get all rides in month",
                "4. Get all rides in year",
                "0. Back to main menu");
        ListView.printMenu(ListView.rideManagerMenuList);
        int choice = AppUtils.getIntWithBound("Input choice", 0, 4);
        switch (choice) {
            case 1:
                printRidesMenu();
                break;
            case 2:
                getRidesFromToUi();


        }
    }

    private static void getRidesFromToUi() {
        LocalDate startDate = AppUtils.getDateWithBound("Input date:"
                , LocalDate.parse("2023-07-01"), LocalDate.now());
        LocalDate endDate = AppUtils.getDateWithBound("Input date:"
                , LocalDate.parse("2023-07-01"), LocalDate.now());
        RideService.printRidesFromTo(startDate, endDate);
    }

    public static void printRidesMenu() {
        System.out.println("1 - WAITING RIDES");
        System.out.println("2 - CONFIRMED RIDES");
        System.out.println("3 - ONGOING RIDES");
        System.out.println("4 - COMPLETED RIDES");
        System.out.println("5 - DECLINE RIDES");
        System.out.println("6 - CANCELLED RIDES");
        int choice = AppUtils.getIntWithBound("Enter a number corresponding to the Ride Status:", 0, 6);
        switch (choice) {
            case 1:
                RideService.getInstance().print(ERideStatus.WAITING);
                break;
            case 2:
                RideService.getInstance().print(ERideStatus.CONFIRMED);
                break;
            case 3:
                RideService.getInstance().print(ERideStatus.ONGOING);
                break;
            case 4:
                RideService.getInstance().print(ERideStatus.COMPLETED);
                break;
            case 5:
                RideService.getInstance().print(ERideStatus.DECLINE);
                break;
            case 6:
                RideService.getInstance().print(ERideStatus.CANCELLED);
                break;
            case 0:
                rideManagerMenu();
                break;
        }
    }
}


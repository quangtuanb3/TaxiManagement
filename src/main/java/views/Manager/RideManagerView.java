package views.Manager;

import Data.Enum.ERideStatus;
import models.Driver;
import services.DriverService;
import services.RideService;
import utils.AppUtils;
import utils.ListView;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

import static views.Manager.MangerView.managerMenu;

public class RideManagerView {
    public static void rideManagerMenu() {
        List<String> rideManagerMenuList = Arrays.asList(
                "Ride Manager Menu",
                "1. Filter rides",
                "2. Print Revenue",
                "0. Back to main menu");
        ListView.printMenu(rideManagerMenuList);
        int choice = AppUtils.getIntWithBound("Input choice", 0, 2);
        switch (choice) {
            case 1 -> filterRidesMenu();
            case 2 -> getRevenueMenu();
            case 0 -> managerMenu();
        }
        rideManagerMenu();
    }

    private static void filterRidesMenu() {
        List<String> filterRidesMenuList = Arrays.asList(
                "Filter ride menu: ",
                "1. Filter ride by status",
                "2. Filter ride from date to date",
                "0. Back to preview menu"
        );
        ListView.printMenu(filterRidesMenuList);
        int choice = AppUtils.getIntWithBound("Input choice: ", 0, 2);
        switch (choice) {
            case 1 -> filterRidesByStatusMenu();
            case 2 -> filterRidesFromToUi();
            case 0 -> rideManagerMenu();
        }
        rideManagerMenu();
    }

    private static void filterRidesFromToUi() {
        System.out.println("2. Filter ride from date to date");
        LocalDate startDate = AppUtils.getDateWithBound("Input start date:"
                , LocalDate.parse("2023-01-01"), LocalDate.now());
        LocalDate endDate = AppUtils.getDateWithBound("Input end date:"
                , startDate, LocalDate.now());
        RideService.printRidesFromTo(startDate, endDate);
        filterRidesMenu();
    }

    public static void filterRidesByStatusMenu() {
        List<String> printRidesByStatusMenuList = Arrays.asList(
                "Filter ride by: ",
                "1 - WAITING RIDES",
                "2 - CONFIRMED RIDES",
                "3 - ONGOING RIDES",
                "4 - COMPLETED RIDES",
                "5 - DECLINE RIDES",
                "6 - CANCELLED RIDES",
                "0 - Back to preview menu");
        ListView.printMenu(printRidesByStatusMenuList);
        int choice = AppUtils.getIntWithBound("Enter a number corresponding to the Ride Status:", 0, 6);
        switch (choice) {
            case 1 -> RideService.getInstance().print(ERideStatus.WAITING);
            case 2 -> RideService.getInstance().print(ERideStatus.CONFIRMED);
            case 3 -> RideService.getInstance().print(ERideStatus.ONGOING);
            case 4 -> RideService.getInstance().print(ERideStatus.COMPLETED);
            case 5 -> RideService.getInstance().print(ERideStatus.DECLINE);
            case 6 -> RideService.getInstance().print(ERideStatus.CANCELLED);
            case 0 -> rideManagerMenu();
        }
        filterRidesByStatusMenu();
    }

    public static void getRevenueMenu() {
        List<String> printRevenueMenuList = Arrays.asList(
                "Get Revenue menu: ",
                "1. Get Revenue daily",
                "2. Get Revenue monthly",
                "3. Get Revenue by Driver",
                "0. Back to preview menu"
        );
        ListView.printMenu(printRevenueMenuList);
        int choice = AppUtils.getIntWithBound("Input choice: ", 0, 3);
        switch (choice) {
            case 1 -> getRevenueDailyUI();
            case 2 -> getRevenueMonthlyUI();
            case 3 -> getRevenueByDriverUI();
            case 0 -> rideManagerMenu();
        }
        getRevenueMenu();
    }

    private static void getRevenueByDriverUI() {
        DriverService.getInstance().print();
        Driver driver = DriverService.getInstance().getById("Input driver id");
        RideService.printRide(driver);
        int numberOfRides = RideService.getNumberOfRides(driver);
        Double total = RideService.calculateRevenue(driver);
        System.out.println("Number of Ride: " + numberOfRides);
        System.out.println("Total revenue: " + AppUtils.convertPrice(total));
    }

    private static void getRevenueMonthlyUI() {
        int year = AppUtils.getIntWithBound("Input year: ", 2023, Year.now().getValue());
        RideService.printRideMonthly(year);

    }

    private static void getRevenueDailyUI() {
        int month = AppUtils.getIntWithBound("Input month: ", 1, 12);
        int year = AppUtils.getIntWithBound("Input year: ", 2023, Year.now().getValue());
        RideService.printRideDaily(month, year);
    }

}


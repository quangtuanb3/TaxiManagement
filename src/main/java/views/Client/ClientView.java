package views.Client;

import Data.Enum.ECarType;
import models.Distance;
import models.Ride;
import services.ClientService;
import services.RideService;
import utils.AppUtils;
import utils.MapQuest;
import utils.ListView;

import java.time.LocalDateTime;
import java.util.Objects;

import static views.LoginView.loginMenu;

public class ClientView {

    public static void clientMenu() {
        ListView.printMenu(ListView.clientMenuList);
        int choice = AppUtils.getIntWithBound("Input choice: ", 0, 6);
        switch (choice) {
            case 1 -> bookRideUI();
            case 2 -> cancelRideUI();
            case 3 -> RideService.getInstance().getRideDetail();
            case 4 -> RideService.printHistory();
            case 5 -> updateUi();
            case 0 -> {
                System.out.println("Back to Login menu");
                loginMenu();
            }
        }
        clientMenu();
    }

    private static void updateUi() {
        ListView.printMenu(ListView.updateClientList);
        int choice = AppUtils.getIntWithBound("Input choice", 0, 3);
        switch (choice) {
            case 1 -> {
                String name = AppUtils.getString("Input new name:");
                ClientService.currentClient.setName(name);
            }
            case 2 -> {
                String password;
                String confirmPassword;
                do {
                    password = AppUtils.getString("Input new password:");
                    confirmPassword = AppUtils.getString("Input confirm password:");
                    if (Objects.equals(confirmPassword, password)) {
                        System.out.println("Confirm password doesn't match with password");
                    }
                } while (!Objects.equals(password, confirmPassword));
                ClientService.currentClient.setPassword(password);
            }
            case 3 -> {
                String phone = AppUtils.getString("Input new phone number:");
                ClientService.currentClient.setName(phone);
            }
            case 0 -> {
                System.out.println("Back to Login menu");
                clientMenu();
            }
        }
        ClientService.getInstance().update(ClientService.currentClient);
    }

    private static void cancelRideUI() {
        if (ClientService.currentClient.getCurrentRide() == null) {
            System.out.println("There is no ride.");
            return;
        }

        System.out.println(ClientService.currentClient.getCurrentRide().toTableRow());
        if (RideService.checkBeforeCancel()) {
            int rideId = AppUtils.getInt("Please input Ride Id to cancel:");
            if (ClientService.currentClient.getCurrentRide().getId() != rideId) {
                System.out.printf("Ride with ID %d not found.\n", rideId);
                int choice = AppUtils.getIntWithBound("Press 1 to continue or 0 to go back to the previous menu", 0, 1);
                if (choice == 1) {
                    cancelRideUI();
                } else {
                    clientMenu();
                }
            } else {

                int choice = AppUtils.getIntWithBound("Are you sure ? Press 1 to cancel or 0 to back preview menu", 0, 1);
                if (choice == 1) {
                    RideService.getInstance().cancelRide(rideId);
                    System.out.println("Your ride has been cancelled!");
                } else {
                    clientMenu();
                }
            }

        } else {
            System.out.println("You just need to end this trip!");
            clientMenu();
        }
    }

    public static void bookRideUI() {
        RideService.autoDeclineRide();
        if (ClientService.currentClient.getCurrentRide() != null) {
            System.out.println("You are already booked a ride. Please come back late");
            return;
        }
        Distance distance = MapQuest.getDistance("Input depart: ", "Input destination: ");
        LocalDateTime pickupTime = AppUtils.getDateTime("Input pickup time");
        int expectedWaitTime = AppUtils.getIntWithBound("Input expected wait time (min)", 0, 5*60);
        int carType = AppUtils.getIntWithBound("Input Car type (1.Four seats/2.Seven seats)", 1, 2);
        Ride ride = RideService.getInstance().bookRide(distance, carType == 1 ? ECarType.FOUR : ECarType.SEVEN, pickupTime, expectedWaitTime);
        System.out.println("Confirm your ride: ");
        int choice = AppUtils.getIntWithBound("Press 1 to book ride or 0 to back preview menu", 0, 1);
        if (choice == 0) {
            bookRideUI();
        } else {
            if (RideService.getInstance().create(ride)) {
                System.out.println("Ride is booked successful!!");
            } else {
                System.out.println("An error occurs. Please try again");
                bookRideUI();
            }
            clientMenu();
        }

    }
}

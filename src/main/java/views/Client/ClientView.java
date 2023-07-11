package views.Client;

import DAO.Enum.ECarType;
import models.Distance;
import models.Location;
import models.Ride;
import services.ClientService;
import services.RideService;
import utils.AppUtils;
import utils.DistanceCalculator;
import utils.ListView;

import java.time.LocalDateTime;
import java.util.Objects;

import static views.LoginView.loginMenu;

public class ClientView {
    static RideService rideService = new RideService();
    static ClientService clientService = new ClientService();

    public static void clientMenu() {
        ListView.printMenu(ListView.clientMenuList);
        int choice = AppUtils.getIntWithBound("Input choice: ", 0, 6);
        switch (choice) {
            case 1:
                bookRideUi();
                clientMenu();
                break;
            case 2:
                cancelRideUi();
                break;
            case 3:
                rideService.getRideDetail();
                break;
            case 4:
                rideService.print();
                break;
            case 5:
                updateUi();
                break;
            case 0:
                System.out.println("Back to Login menu");
                loginMenu();
                break;
        }
    }

    private static void updateUi() {
        ListView.printMenu(ListView.updateClientList);
        int choice = AppUtils.getIntWithBound("Input choice", 0, 3);
        switch (choice) {
            case 1:
                String name = AppUtils.getString("Input new name:");
                ClientService.currentClient.setName(name);
                break;
            case 2:
                String password;
                String confirmPassword;
                do {
                    password = AppUtils.getString("Input new password:");
                    confirmPassword = AppUtils.getString("Input confirm password:");
                } while (!Objects.equals(password, confirmPassword));
                ClientService.currentClient.setPassword(password);
                break;
            case 3:
                String phone = AppUtils.getString("Input new phone number:");
                ClientService.currentClient.setName(phone);
                break;
            case 0:
                System.out.println("Back to Login menu");
                clientMenu();
                break;

        }
        clientService.update(ClientService.currentClient);
    }

    private static void cancelRideUi() {
        System.out.println(RideService.currentRide.toString());
        if (RideService.checkBeforeCancel()) {
            int rideId = AppUtils.getInt("Please input Ride Id to cancel:");
            if (RideService.currentRide.getId() != rideId) {
                System.out.printf("Not found %d.\n", rideId);
                int choice = AppUtils.getIntWithBound("Press 1 to continue or 0 to back to preview menu", 0, 1);
                if (choice == 1) {
                    cancelRideUi();
                } else clientMenu();
            }
            rideService.cancelRide(rideId);
        } else {
            System.out.println("You just need to end this trip!");
            clientMenu();
        }
    }

    public static void bookRideUi() {
        if (RideService.currentRide != null) {
            System.out.println("You are already booked a ride. Please come back late");
            return;
        }
        Distance distance = DistanceCalculator.getDistance("Input depart: ", "Input destination: ");
        LocalDateTime pickupTime = AppUtils.getDateTime("Input pickup time");
        int expectedWaitTime = AppUtils.getIntWithBound("Input expected wait time", 10, 1000);
        int carType = AppUtils.getIntWithBound("Input Car type (1.Four seats/2.Seven seats)", 1, 2);
        Ride ride = rideService.bookRide(new Location(distance.getDepart()), new Location(distance.getArrive()), carType == 1 ? ECarType.FOUR : ECarType.SEVEN, pickupTime, expectedWaitTime);
        System.out.println("Confirm your ride: ");
        int choice = AppUtils.getIntWithBound("Press 1 to book ride or 0 to back preview menu", 0, 1);
        if (choice == 0) {
            bookRideUi();
        } else {
            rideService.create(ride);
            System.out.println("Ride is booked successful!!");
            clientMenu();
        }

    }
}

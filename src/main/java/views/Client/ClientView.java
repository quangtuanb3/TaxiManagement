package views.Client;

import database.Enum.ECarType;
import models.Location;
import models.Ride;
import services.ClientService;
import services.RideService;
import utils.AppUtils;

import java.time.LocalDateTime;
import java.util.Objects;

import static views.LoginView.loginMenu;

public class ClientView {
    static RideService rideService = new RideService();
    static ClientService clientService = new ClientService();

    public static void clientMenu() {
        try {
            int choice;
            do {
                System.out.println("Client menu");
                System.out.println("1. Book ride");
                System.out.println("2. Cancel ride");
                System.out.println("3. Get ride detail");
                System.out.println("4. Get ride history");
                System.out.println("5. Edit profile");
                System.out.println("0. Back to login menu");
                choice = AppUtils.getIntWithBound("Input choice: ", 0, 6);
                switch (choice) {
                    case 1:
                        rideService.create(bookRideUi());
                        break;
                    case 2:
                        rideService.cancelRide(cancelRideUi());
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
            while (choice != 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void updateUi() {
        try {

            System.out.println("Select field you want to update: ");
            System.out.println("1. Name");
            System.out.println("2. Password");
            System.out.println("3. Phone number");
            System.out.println("0. Back to Client menu");
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int cancelRideUi() {
        rideService.print();
        int rideId = AppUtils.getInt("Input ride id: ");
        if (!rideService.isExist(rideId)) {
            System.out.printf("Not found %d.\n", rideId);
            cancelRideUi();
        }
        return rideId;
    }

    private static Ride bookRideUi() {
        String departAddress = AppUtils.getString("Input depart: ");
        String arriverAddress = AppUtils.getString("Input destination: ");
        LocalDateTime pickupTime = AppUtils.getDateTime("Input pickup time");
        int carType = AppUtils.getIntWithBound("Input Car type (1.Four seats/2.Seven seats)", 1, 2);
        return rideService.bookRide(new Location(departAddress), new Location(arriverAddress), carType == 1 ? ECarType.FOUR : ECarType.SEVEN, pickupTime);

    }
}

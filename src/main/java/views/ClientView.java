package views;

import models.Client;
import models.CurrentUser;
import models.Location;
import models.Ride;
import services.ClientService;
import services.RideService;
import utils.AppUtils;

import java.io.IOException;

import static views.LoginView.loginMenu;

public class ClientView {
    static RideService rideService = new RideService();
    static ClientService clientService = new ClientService();

    public static void clientMenu() throws IOException {
        int choice;
        do {
            System.out.println("Client menu");
            System.out.println("1. Book ride");
            System.out.println("2. Cancel ride");
            System.out.println("3. Get ride detail");
            System.out.println("4. Get ride history");
            System.out.println("5. Change password");
            System.out.println("0. Back to login menu");
            choice = AppUtils.getIntWithBound("Input choice: ",0, 6);
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
    }

    private static int updateUi() {
        Client client = ClientService.getByUsername(CurrentUser.currentUser);
        System.out.println(">>>>>curent"+ CurrentUser.currentUser);
//        System.out.println(">>>>>>"+ clients.csv);
        System.out.println("Select field you want to update: ");
//        int choice = getValue.getInBoundary(0, clients.csv.getClass().getDeclaredFields().length);
//        System.out.println(choice);
        return 1;
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
        return rideService.bookRide(new Location(departAddress), new Location(arriverAddress));
    }

}

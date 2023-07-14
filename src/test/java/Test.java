import Data.Enum.ECarType;
import models.Distance;
import models.Ride;
import services.ClientService;
import services.RideService;
import utils.AppUtils;
import utils.MapQuest;

import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {

        caseGetAddress();
    }
    public static void caseBookRide() {
        ClientService.currentClient = ClientService.listClients.get(0);
        RideService.autoDeclineRide();
        if (RideService.currentRide != null) {
            System.out.println("You are already booked a ride. Please come back late");
            return;
        }
        Distance distance = new Distance();
        distance.setArrive("Sân Bay Phú Bài, Hương Thủy, Thừa Thiên Huế, Việt Nam");
        distance.setDepart("Thị trấn A Lưới, A Lưới, Thừa Thiên Huế, Việt Nam");
        distance.setDistance(10.5D);

        LocalDateTime pickupTime = AppUtils.parseDateTime("2023-07-13 15:00:00");
        int expectedWaitTime = 10;
        Ride ride = RideService.getInstance().bookRide(distance, ECarType.FOUR, pickupTime, expectedWaitTime);
        System.out.println("Confirm your ride: ");
        if (RideService.getInstance().create(ride)) {
            System.out.println("Ride is booked successful!!");
        } else {
            System.out.println("An error occurs. Please try again");
        }
    }

    public static void caseGetAddress() {
        String address = MapQuest.getAddress("Input Address: ");
    }

}



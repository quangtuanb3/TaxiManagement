import Data.Enum.ECarType;
import Data.Enum.ERideStatus;
import models.Distance;
import models.Location;
import models.Ride;
import services.ClientService;
import services.DriverService;
import services.RideService;
import utils.AppUtils;
import utils.MapQuest;

import java.time.LocalDateTime;

import static services.ClientService.listClients;

public class Test {

    @org.junit.Test
    public void caseBookRide() {
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

    @org.junit.Test
    public void caseGetAddress() {
        String address = MapQuest.getAddress("Input Address: ");
        System.out.println(address);
    }

    @org.junit.Test
    public void printAvailableRides() {
        DriverService.currentDriver = DriverService.getByEmail("duytham@gmail.com");


        Ride ride1 = new Ride();
        ride1.setId(8);
        ride1.setClient(listClients.get(1));
        ride1.setCarType(ECarType.FOUR);
        ride1.setPickupLocation(new Location("153 Phan Bội Châu, phường Trường An, Huế, Thừa Thiên Huế"));
        ride1.setActualDestination(new Location("Sân Bay Phú Bài, Hương Thủy, Thừa Thiên Huế, Việt Nam"));
        ride1.setExpectedDestination(new Location("20 Nguyễn Trãi, Tây Lộc, Huế, Thừa Thiên Huế"));
        ride1.setFare(200000D);
        ride1.setExpectedDistance(15.5D);
        ride1.setActualDistance(4.7D);
        ride1.setStatus(ERideStatus.WAITING);
        ride1.setExpectedPickupTime(AppUtils.parseDateTime("2023-07-14 23:00:00"));
        ride1.setBookTime(AppUtils.parseDateTime("2023-07-14 20:32:00"));
        ride1.setExpectedWaitTime(30);
        RideService.listRides.add(ride1);
        RideService.waitingRides.add(ride1);


        RideService.listRides.forEach(ride -> System.out.println(ride.toTableRow()));
        RideService.printAvailableRides();
    }

}



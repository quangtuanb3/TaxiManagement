import services.DriverService;
import services.RideService;

import static DAO.Startup.init;

public class Test {


    public static void main(String[] args) {
//        LocalDateTime dateTime = getDateTime("Input start time");
//        System.out.println(dateTime);
//      int duration =  getDuration(getDateTimeNow(), parseDateTime("2023-07-09 20:30:10"));
//        System.out.println(duration);
      init();
        DriverService.currentDriver = DriverService.listDrivers.get(0);
        RideService.printAvailableRides();

    }
}

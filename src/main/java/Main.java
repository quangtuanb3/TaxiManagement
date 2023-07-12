import services.RideService;

import java.time.LocalDate;

import static Data.Startup.init;

public class Main {
    public static void main(String[] args) {
        init();
//        loginMenu();
        LocalDate startDate = LocalDate.parse("2023-07-02");
        LocalDate endDate = LocalDate.parse("2023-07-12");
        RideService.printRidesFromTo(startDate, endDate);
    }
}

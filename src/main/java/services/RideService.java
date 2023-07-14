package services;

import Data.Enum.ECarType;
import Data.Enum.EDriverStatus;
import Data.Enum.EPath;
import Data.Enum.ERideStatus;
import models.Distance;
import models.Driver;
import models.Location;
import models.Ride;
import utils.AppUtils;
import utils.Constant;
import utils.MapQuest;
import utils.Serializable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;

import static utils.AppUtils.getDateTimeNow;
import static utils.AppUtils.getDuration;


public class RideService implements BasicCRUD<Ride> {
    public static List<Ride> listRides;
    public static List<Ride> waitingRides;

    static {
        listRides = new ArrayList<>((List<Ride>) Serializable.deserialize(EPath.RIDES.getFilePath()));
        if (waitingRides != null) {
            availableRides = waitingRides.stream()
                    .filter(e -> getDuration(getDateTimeNow(), e.getExpectedPickupTime()) < 60)
                    .filter(e -> e.getCarType().equals(DriverService.currentDriver.getCar().getCarType()))
                    .filter(e -> MapQuest.calculateDistance(e.getPickupLocation().getAddress(),
                            DriverService.currentDriver.getLocation().getAddress()) < Constant.MAX_RANGE)
                    .toList();
        }
    }

    private static RideService instance;

    public static RideService getInstance() {
        if (instance == null) {
            instance = new RideService();
        }
        return instance;
    }

    public static List<Ride> availableRides;
    public static List<Driver> availableDriver;

    public static Ride currentRide;
    public static FareCalculator fareCalculator = new FareCalculator();

    public RideService() {
    }

    public static void save() {
        Serializable.serialize(listRides, EPath.RIDES.getFilePath());
    }

    public static boolean checkBeforeCancel() {
        if (currentRide.isWaitApprove()) {
            return true;
        }
        return currentRide.isConfirmed();
    }

    public static void printHistory() {
        List<Ride> history = listRides.stream().filter(e -> Objects.equals(e.getClient().getId(), ClientService.currentClient.getId())).toList();
        if (history.size() == 0) {
            System.out.println("There is no booked ride");
            return;
        }
        printListRides(history);
    }

    public static void printRide(Driver driver) {
        List<Ride> rides = listRides.stream().filter(e -> Objects.equals(e.getDriver().getId(), driver.getId())).toList();
        if (rides.size() == 0) {
            return;
        }
        printListRides(rides);
    }


    public static void printRideDaily(int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        Map<LocalDate, Integer> ridesPerDay = new HashMap<>();
        Map<LocalDate, Double> farePerDay = new HashMap<>();

        for (Ride ride : listRides) {
            LocalDate rideDate = ride.getEndTime().toLocalDate();
            if (rideDate.getMonthValue() == month && rideDate.getYear() == year) {
                int ridesCount = ridesPerDay.getOrDefault(rideDate, 0);
                double fareSum = farePerDay.getOrDefault(rideDate, 0.0);
                ridesPerDay.put(rideDate, ridesCount + 1);
                farePerDay.put(rideDate, fareSum + ride.getFare());
            }
        }

        System.out.println("| Date       | Number of rides | Total fare     |");
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);
            int ridesCount = ridesPerDay.getOrDefault(currentDate, 0);
            double fareSum = farePerDay.getOrDefault(currentDate, 0.0);
            System.out.printf("| %s | %-15d | %-14.2f |\n", currentDate.toString(), ridesCount, fareSum);
        }
    }


    public static void printRideMonthly(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        List<Ride> rides = listRides.stream()
                .filter(e -> e.getEndTime().toLocalDate().isAfter(startDate) && e.getEndTime().toLocalDate().isBefore(endDate))
                .toList();

        Map<Month, Integer> ridesPerMonth = new HashMap<>();
        Map<Month, Double> farePerMonth = new HashMap<>();

        for (Ride ride : rides) {
            Month month = ride.getEndTime().getMonth();
            int ridesCount = ridesPerMonth.getOrDefault(month, 0);
            double fareSum = farePerMonth.getOrDefault(month, 0.0);
            ridesPerMonth.put(month, ridesCount + 1);
            farePerMonth.put(month, fareSum + ride.getFare());
        }
        System.out.println("| Month      | Number of rides | Total fare     |");
        for (Month month : Month.values()) {
            int ridesCount = ridesPerMonth.getOrDefault(month, 0);
            double fareSum = farePerMonth.getOrDefault(month, 0.0);
            System.out.printf("| %-10s | %-15d | %-14.2f |\n", month.toString(), ridesCount, fareSum);
        }
    }


    public static Double calculateRevenue(Driver driver) {
        List<Ride> rides = listRides.stream().filter(e -> Objects.equals(e.getDriver().getId(), driver.getId())).toList();
        Double total = 0D;
        for (Ride r : rides) {
            if (r.getFare() != null) {
                total += r.getFare();
            }

        }
        return total;
    }

    public static int getNumberOfRides(Driver driver) {
        return listRides.stream().filter(e -> Objects.equals(e.getDriver().getId(), driver.getId())).toList().size();
    }


    @Override
    public List<Ride> getAll() {
        return listRides;
    }

    @Override
    public boolean create(Ride ride) {
        listRides.add(ride);
        return true;
    }

    public Ride bookRide(Distance distance, ECarType eCarType, LocalDateTime expectedPickupTime, int expectedWaitTime) {
        try {
            Ride ride = new Ride(ClientService.currentClient, eCarType, distance, null, expectedPickupTime, expectedWaitTime);
            Double fare = fareCalculator.firstCalculateFare(ride);
            ride.setFare(fare);
            currentRide = ride;
            if (waitingRides == null) {
                waitingRides = new ArrayList<>();
                waitingRides.add(ride);
            }
            waitingRides.add(ride);
            printExpectedRide(ride);
            return ride;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void update(Ride ride) {
        for (int i = 0; i < listRides.size(); i++) {
            Ride existingRide = listRides.get(i);
            if (existingRide.getId() == ride.getId()) {
                listRides.set(i, ride);
                break;
            }
        }
    }

    @Override
    public boolean isExist(int rideId) {
        Ride ride = listRides.stream()
                .filter(e -> Objects.equals(e.getId(), rideId))
                .findFirst()
                .orElse(null);
        return ride != null;
    }

    //    public check
    @Override
    public void delete(int rideId) {
        listRides = listRides.stream()
                .filter(e -> !Objects.equals(e.getId(), rideId))
                .toList();
    }

    public void print(ERideStatus eRideStatus) {
        List<Ride> list = listRides.stream().filter(e ->
                e.getStatus().equals(eRideStatus)).toList();
        if (list.size() != 0) {
            printListRides(list);
        } else {
            System.out.println("There is no ride!");
        }
    }

    private static void printListRides(List<Ride> rides) {
//        "| %-4s | %-19s | %-19s | %-75s | %-75s | %-15s | %-15s | %-15s
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("| ID   | Client Name         | Driver Name         | Pickup Location                                                             | Destination                                                                 | Distance (km)   | Wait time (min) | Fare (vnd)      |\n");
        tableBuilder.append("|------|---------------------|---------------------|-----------------------------------------------------------------------------|-----------------------------------------------------------------------------|-----------------|-----------------|-----------------|\n");
        for (Ride ride : rides) {
            tableBuilder.append(ride.toTableRow());
        }
        System.out.println(tableBuilder);
    }

    public void printAll() {
        printListRides(listRides);
    }

    public static void updateRideStatus(Ride ride, ERideStatus status) {
        ride.setStatus(status);
    }


    @Override
    public Ride getById(String str) {
        int rideId = AppUtils.getInt(str);
        Ride ride = listRides.stream().filter(e -> e.getId() == rideId).findFirst().orElse(null);
        if (ride == null) {
            System.out.println("Driver not found. Please try again!");
            getById(str);
        }
        return ride;
    }

    public Ride getById(int id) {
        return listRides.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean cancelRide(int rideId) {
        getById(rideId).setStatus(ERideStatus.CANCELLED);
        getById(rideId).setFare(0D);
        RideService.currentRide = null;
        return true;
    }

    public void getRideDetail() {
        if (currentRide == null) {
            System.out.println("There is no ride");
            return;
        }
        System.out.println(currentRide.toString());
    }

    public void approve(int rideId) {
        Ride ride = getById(rideId);
        if (DriverService.currentDriver.isAvailable()) {
            assignRideToDriver(ride, DriverService.currentDriver);
            waitingRides = waitingRides.stream().filter(e -> e.getId() != rideId).toList();
        } else {
            DriverService.currentDriver.setDriverStatus(EDriverStatus.UNAVAILABLE);
        }
    }

    public void assignRideToDriver(Ride ride, Driver driver) {
        currentRide = ride;
        driver.setCurrentRide(ride);
        driver.setDriverStatus(EDriverStatus.ON_TRIP);
        currentRide.setStatus(ERideStatus.CONFIRMED);
        currentRide.setDriver(driver);
        currentRide.setConfirmedTime(getDateTimeNow());
    }

    public void finishRide(Location actualDestination, int actualWaitTime) {
        currentRide.setActualDestination(actualDestination);
        Double actualDistance = MapQuest.calculateDistance(currentRide.getPickupLocation().getAddress(), actualDestination.getAddress());
        currentRide.setActualDistance(actualDistance);
        currentRide.setActualWaitTime(actualWaitTime);
        double fare = fareCalculator.lastCalculateFare(currentRide);
        currentRide.setFare(fare);
        currentRide.setEndTime(getDateTimeNow());
        currentRide.setStatus(ERideStatus.COMPLETED);
        printActualRide(currentRide);
        update(currentRide);
        currentRide = null;
        DriverService.currentDriver.setCurrentRide(null);
        DriverService.currentDriver.setDriverStatus(EDriverStatus.AVAILABLE);
    }

    public static boolean printAvailableRides() {
        if (availableRides == null) {
            System.out.println("There is no ride available");
            return false;
        }
        printListRides(availableRides);
        return true;
    }

    public static void autoDeclineRide() {
        if (waitingRides == null) {
            return;
        }
        for (Ride ride : waitingRides) {
            if (getDuration(ride.getExpectedPickupTime(), getDateTimeNow()) > 20) {
                if (Objects.equals(currentRide, ride)) {
                    currentRide = null;
                }
                ride.setStatus(ERideStatus.DECLINE);
                waitingRides.remove(ride);
            }
        }
    }

    public static void printExpectedRide(Ride ride) {
        if (ride == null) {
            System.out.println("There is no such ride");
            return;
        }
        System.out.printf("%d \t %s \t \t %s \t %s \t\t %d \t %f \t %d\t %s \n",
                ride.getId(),
                ride.getClient().getName(),
                ride.getPickupLocation().getAddress(),
                ride.getExpectedDestination().getAddress(),
                ride.getCarType().getSeat(),
                ride.getExpectedDistance(),
                ride.getExpectedWaitTime(),
                AppUtils.convertPrice(ride.getFare()));
    }

    public static void printActualRide(Ride ride) {
        if (ride == null) {
            System.out.println("There is no such ride");
            return;
        }
        System.out.printf("%d \t %s \t \t %s \t %s \t\t %d \t %f \t %d\t %s",
                ride.getId(),
                ride.getClient().getName(),
                ride.getPickupLocation().getAddress(),
                ride.getActualDestination().getAddress(),
                ride.getCarType().getSeat(),
                ride.getActualDistance(),
                ride.getActualWaitTime(),
                AppUtils.convertPrice(ride.getFare()));
    }

    public boolean isInList(List<Ride> list, int rideId) {
        return list.stream().anyMatch(e -> e.getId() == rideId);
    }

    public static void printRidesFromTo(LocalDate start, LocalDate end) {
        List<Ride> ridesInRange = listRides.stream()
                .filter(ride -> ride.getStartTime().toLocalDate().isAfter(start.minusDays(1))
                        && ride.getEndTime().toLocalDate().isBefore(end.plusDays(1)))
                .toList();

        if (ridesInRange.size() != 0) {
            printListRides(ridesInRange);
        } else {
            System.out.printf("There is no ride from %s to %s \n", start.toString(), end.toString());
        }
    }

}

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
        listRides = loadData();
        waitingRides = new ArrayList<>(listRides.stream().filter(e -> e.getStatus().equals(ERideStatus.WAITING)).toList());
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

    public static FareCalculator fareCalculator = new FareCalculator();

    public RideService() {
    }

    public static void save() {
        Serializable.serialize(listRides, EPath.RIDES.getFilePath());
    }

    public static boolean checkBeforeCancel() {
        return ClientService.currentClient.getCurrentRide().isWaitApprove();
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

    public static List<Ride> getWaitingRides() {
        if (listRides == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(listRides.stream().filter(e -> e.getStatus().equals(ERideStatus.WAITING)).toList());
    }

    public static void printRideDaily(int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        Map<LocalDate, Integer> ridesPerDay = new HashMap<>();
        Map<LocalDate, Double> farePerDay = new HashMap<>();

        for (Ride ride : getCompletedRide()) {
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
            System.out.printf("| %s | %-15d | %-14s |\n", currentDate.toString(), ridesCount, AppUtils.convertPrice(fareSum));
        }
    }

    private static List<Ride> getCompletedRide() {
        if (listRides == null) return new ArrayList<>();
        return listRides.stream().filter(e -> Objects.equals(e.getStatus(), ERideStatus.COMPLETED)).toList();
    }

    public static void printRideMonthly(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        List<Ride> rides = getCompletedRide().stream()
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
            System.out.printf("| %-10s | %-15d | %-14s|\n", month.toString(), ridesCount, AppUtils.convertPrice(fareSum));
        }
    }


    public static Double calculateRevenue(Driver driver) {
        List<Ride> rides = getCompletedRide().stream().filter(e -> Objects.equals(e.getDriver().getId(), driver.getId())).toList();
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
        save();
        return true;
    }

    public Ride bookRide(Distance distance, ECarType eCarType, LocalDateTime expectedPickupTime, int expectedWaitTime) {
        try {
            Ride ride = new Ride(ClientService.currentClient, eCarType, distance, null, expectedPickupTime, expectedWaitTime);
            Double fare = fareCalculator.calculateFirstFare(ride);
            ride.setFare(fare);
            ClientService.currentClient.setCurrentRide(ride);
            if (getWaitingRides().size() == 0) {
                waitingRides = new ArrayList<>();
                waitingRides.add(ride);
            }
            waitingRides.add(ride);
            System.out.print("| ID   | Client Name         | Driver Name         | " +
                    "Pickup Location                                                             |" +
                    " Destination                                                                 | " +
                    "Distance (km)   | Wait time (min) | Pickup time          |Fare (vnd)       |\n");
            System.out.print("|------|---------------------|---------------------|" +
                    "-----------------------------------------------------------------------------|" +
                    "-----------------------------------------------------------------------------|" +
                    "-----------------|-----------------|----------------------|-----------------|\n");
            System.out.println(ride.toTableRow());
            ClientService.save();
            save();
            return ride;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void update(Ride ride) {
        listRides.stream()
                .filter(existingRide -> existingRide.getId() == ride.getId())
                .findFirst()
                .ifPresent(existingRide -> listRides.set(listRides.indexOf(existingRide), ride));
        save();
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

    public static void printListRides(List<Ride> rides) {
        if (rides.size() == 0) {
            System.out.println("There is no ride");
            return;
        } else {
            if (rides.get(0) == null) {
                System.out.println("There is no ride");
                return;
            }
        }
//        "| %-4s | %-19s | %-19s | %-75s | %-75s | %-15s | %-15s | %-15s
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("| ID   | Client Name         | Driver Name         | " +
                "Pickup Location                                                             |" +
                " Destination                                                                 | " +
                "Distance (km)   | Wait time (min) | Pickup time          |Fare (vnd)       |\n");
        tableBuilder.append("|------|---------------------|---------------------|" +
                "-----------------------------------------------------------------------------|" +
                "-----------------------------------------------------------------------------|" +
                "-----------------|-----------------|----------------------|-----------------|\n");
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
        ClientService.currentClient.setCurrentRide(null);
        save();
        ClientService.save();
        return true;
    }

    public void getRideDetail() {
        if (ClientService.currentClient.getCurrentRide() == null) {
            System.out.println("There is no ride");
            return;
        }
        System.out.print("| ID   | Client Name         | Driver Name         | " +
                "Pickup Location                                                             |" +
                " Destination                                                                 | " +
                "Distance (km)   | Wait time (min) | Pickup time          |Fare (vnd)       |\n");
        System.out.print("|------|---------------------|---------------------|" +
                "-----------------------------------------------------------------------------|" +
                "-----------------------------------------------------------------------------|" +
                "-----------------|-----------------|----------------------|-----------------|\n");

        System.out.println(ClientService.currentClient.getCurrentRide().toTableRow());
    }

    public boolean approve(int rideId) {
        Ride ride = getById(rideId);
        assignRideToDriver(ride, DriverService.currentDriver);
        return true;
    }

    public void assignRideToDriver(Ride clientRide, Driver driver) {
        driver.setCurrentRide(clientRide);
        driver.setDriverStatus(EDriverStatus.ON_TRIP);
        driver.getCurrentRide().setStatus(ERideStatus.CONFIRMED);
        clientRide.setDriver(driver);
        clientRide.setConfirmedTime(getDateTimeNow());
        Objects.requireNonNull(ClientService.getClientByRideId(clientRide.getId())).setCurrentRide(clientRide);
        waitingRides = waitingRides.stream().filter(e -> e.getId() != clientRide.getId()).toList();
        RideService.save();
        DriverService.save();
        ClientService.save();
    }

    public void finishRide(Location actualDestination, int actualWaitTime) {
        DriverService.currentDriver.getCurrentRide().setActualDestination(actualDestination);
        Double actualDistance = MapQuest.calculateDistance(DriverService.currentDriver.getCurrentRide()
                .getPickupLocation().getAddress(), actualDestination.getAddress());
        DriverService.currentDriver.getCurrentRide().setActualDistance(actualDistance);
        DriverService.currentDriver.getCurrentRide().setActualWaitTime(actualWaitTime);
        double fare = fareCalculator.calculateLastFare(DriverService.currentDriver.getCurrentRide());
        DriverService.currentDriver.getCurrentRide().setFare(fare);
        DriverService.currentDriver.getCurrentRide().setEndTime(getDateTimeNow());
        DriverService.currentDriver.getCurrentRide().setStatus(ERideStatus.COMPLETED);
        update(DriverService.currentDriver.getCurrentRide());
        printListRides(Collections.singletonList(DriverService.currentDriver.getCurrentRide()));
        Objects.requireNonNull(ClientService.getClientByRideId(DriverService.currentDriver.getCurrentRide().getId())).setCurrentRide(null);
        DriverService.currentDriver.setCurrentRide(null);
        DriverService.currentDriver.setDriverStatus(EDriverStatus.AVAILABLE);
        DriverService.save();
        ClientService.save();
        RideService.save();
    }

    public static boolean printAvailableRides() {
        List<Ride> rides = getAvailableRides();
        if (rides.size() == 0) {
            System.out.println("There is no ride available");
            return false;
        }
        printListRides(rides);
        return true;
    }

    public static List<Ride> getAvailableRides() {
        if (getWaitingRides().size() == 0) {
            return new ArrayList<>();
        }
        return getWaitingRides().stream()
                .filter(e -> getDuration(getDateTimeNow(), e.getExpectedPickupTime()) < 120)
                .filter(e -> e.getCarType().equals(DriverService.currentDriver.getCar().getCarType()))
                .filter(e -> MapQuest.calculateDistance(e.getPickupLocation().getAddress(),
                        DriverService.currentDriver.getLocation().getAddress()) < Constant.MAX_RANGE)
                .toList();
    }

    public static void autoDeclineRide() {
        if (getWaitingRides().size() == 0) {
            return;
        }
        for (Ride ride : getWaitingRides()) {
            if (getDuration(ride.getExpectedPickupTime(), getDateTimeNow()) > 20) {
                if (Objects.equals(ClientService.currentClient.getCurrentRide().getId(), ride.getId())) {
                    ClientService.currentClient.setCurrentRide(null);
                }
                ride.setStatus(ERideStatus.DECLINE);
                getWaitingRides().remove(ride);
                save();
            }
        }
    }

    public static void printExpectedRide(Ride ride) {
        if (ride == null) {
            System.out.println("There is no such ride");
            return;
        }
        System.out.print("| ID   | Client Name         | Driver Name         | " +
                "Pickup Location                                                             |" +
                " Destination                                                                 | " +
                "Distance (km)   | Wait time (min) | Pickup time          |Fare (vnd)       |\n");
        System.out.print("|------|---------------------|---------------------|" +
                "-----------------------------------------------------------------------------|" +
                "-----------------------------------------------------------------------------|" +
                "-----------------|-----------------|----------------------|-----------------|\n");
        System.out.printf("| %-4d | %-19s | %-19s | %-19s | %-75s | %-75s | %-15f | %-15d | %-20s | %-15s |\n",
                ride.getId(),
                ride.getClient().getName(),
                ride.getDriver().getName(),
                ride.getPickupLocation().getAddress(),
                ride.getExpectedDestination().getAddress(),
                ride.getCarType().getSeat(),
                ride.getExpectedDistance(),
                ride.getExpectedWaitTime(),
                ride.getExpectedPickupTime().format(Constant.DATE_TIME_FORMATTER),
                AppUtils.convertPrice(ride.getFare()));
    }

    public static void printActualRide(Ride ride) {
        if (ride == null) {
            System.out.println("There is no such ride");
            return;
        }
        System.out.print("| ID   | Client Name         | Driver Name         | " +
                "Pickup Location                                                             |" +
                " Destination                                                                 | " +
                "Distance (km)   | Wait time (min) | Pickup time          |Fare (vnd)       |\n");
        System.out.print("|------|---------------------|---------------------|" +
                "-----------------------------------------------------------------------------|" +
                "-----------------------------------------------------------------------------|" +
                "-----------------|-----------------|----------------------|-----------------|\n");
        System.out.printf("| %-4d | %-19s | %-19s | %-19s | %-75s | %-75s | %-15f | %-15d | %-20s | %-15s |\n",
                ride.getId(),
                ride.getClient().getName(),
                ride.getDriver().getName(),
                ride.getPickupLocation().getAddress(),
                ride.getActualDestination().getAddress(),
                ride.getCarType().getSeat(),
                ride.getActualDistance(),
                ride.getActualWaitTime(),
                ride.getStartTime().format(Constant.DATE_TIME_FORMATTER),
                AppUtils.convertPrice(ride.getFare()));
    }

    public boolean isInList(List<Ride> list, int rideId) {
        return list.stream().anyMatch(e -> e.getId() == rideId);
    }

    public static void printRidesFromTo(LocalDate start, LocalDate end) {
        List<Ride> ridesInRange = getCompletedRide().stream()
                .filter(ride -> ride.getStartTime().toLocalDate().isAfter(start.minusDays(1))
                        && ride.getEndTime().toLocalDate().isBefore(end.plusDays(1)))
                .toList();

        if (ridesInRange.size() != 0) {
            printListRides(ridesInRange);
        } else {
            System.out.printf("There is no ride from %s to %s \n", start.toString(), end.toString());
        }
    }

    public static List<Ride> loadData() {
        return new ArrayList<>((List<Ride>) Serializable.deserialize(EPath.RIDES.getFilePath()));
    }
}

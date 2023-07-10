package services;

import DAO.Enum.ECarType;
import DAO.Enum.EDriverStatus;
import DAO.Enum.ERideStatus;
import models.Driver;
import models.Location;
import models.Ride;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static utils.AppUtils.getDateTimeNow;
import static utils.AppUtils.getDuration;


public class RideService implements BasicCRUD<Ride> {
    public static List<Ride> listRides;
    public static List<Driver> availableDriver;
    public static List<Ride> waitingRides;
    public static Ride currentRide;
    public static FareCalculator fareCalculator = new FareCalculator();


    public RideService() {
    }


    @Override
    public List<Ride> getAll() {
        return listRides;
    }

    @Override
    public void create(Ride ride) {
        listRides.add(ride);
    }

    public Ride bookRide(Location pickupLocation, Location expectedDestination, ECarType eCarType, LocalDateTime expectedPickupTime, int expectedWaitTime) {
        try {
            Ride ride = new Ride(ClientService.currentClient, eCarType, pickupLocation, expectedDestination, null, expectedPickupTime, expectedWaitTime);
            Double fare = fareCalculator.firstCalculateFare(ride);
            ride.setFare(fare);
            currentRide = ride;
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
    public boolean isExist(List<Ride> listRides, int rideId) {
        Ride ride = listRides.stream()
                .filter(e -> Objects.equals(e.getId(), rideId))
                .findFirst()
                .orElse(null);
        return ride != null;
    }

    @Override
    public void delete(int rideId) {
        listRides = listRides.stream()
                .filter(e -> !Objects.equals(e.getId(), rideId))
                .collect(Collectors.toList());
    }

    public void print() {
        for (Ride ride : listRides) {
            System.out.println(ride.toString());
        }
    }

    public static void updateRideStatus(Ride ride, ERideStatus status) {
        ride.setStatus(status);
    }


    public Ride getById(int id) {
        return listRides.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void cancelRide(int rideId) {
        getById(rideId).setStatus(ERideStatus.CANCELLED);
        RideService.currentRide = null;
    }

    public void getRideDetail() {
        System.out.println(RideService.currentRide.toString());
    }

    public void approve(int rideId) {
        Ride ride = getById(rideId);
        if (DriverService.currentDriver.isAvailable()) {
            assignRideToDriver(ride, DriverService.currentDriver);
            waitingRides = waitingRides.stream().filter(e -> e.getId() != rideId).collect(Collectors.toList());
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

    public static void printAvailableRides() {
        List<Ride> availableRides = waitingRides.stream()
                .filter(e -> getDuration(getDateTimeNow(), e.getExpectedPickupTime()) < 60)
                .collect(Collectors.toList())
                .stream()
                .filter(e -> e.getCarType().equals(DriverService.currentDriver.getCar().getCarType()))
                .collect(Collectors.toList());

        System.out.println("ID\tName\t\tPickup\t\t\tDestination\t\tFare");
        System.out.println("----------------------------------------------------------");
        autoDeclineRide();
        for (Ride ride : availableRides) {
            System.out.printf("%s\t%s\t\t%s\t%s\t\t%.2f%n",
                    ride.getId(),
                    ride.getClient().getName(),
                    ride.getPickupLocation().getAddress(),
                    ride.getExpectedDestination().getAddress(),
                    ride.getFare());
        }
    }

    public static void autoDeclineRide() {
        for (Ride ride : waitingRides) {
            if (getDuration(ride.getExpectedPickupTime(), getDateTimeNow()) > 20) {
                ride.setStatus(ERideStatus.DECLINE);
                waitingRides.remove(ride);
                //send email here
            }
        }
    }

    public static void printExpectedRide(Ride ride) {
        if (ride == null) {
            System.out.println("There is no such ride");
            return;
        }
        System.out.printf("%d \t %s \t \t %s \t %s \t\t %d \t %f \t %d\t %f \n",
                ride.getId(),
                ride.getClient().getName(),
                ride.getPickupLocation().getAddress(),
                ride.getExpectedDestination().getAddress(),
                ride.getCarType().getSeat(),
                fareCalculator.getExpectedDistance(),
                ride.getExpectedWaitTime(),
                ride.getFare());
    }

    public static void printActualRide(Ride ride) {
        if (ride == null) {
            System.out.println("There is no such ride");
            return;
        }
        System.out.printf("%d \t %s \t \t %s \t %s \t\t %d \t %f \t %d\t %f",
                ride.getId(),
                ride.getClient().getName(),
                ride.getPickupLocation().getAddress(),
                ride.getActualDestination().getAddress(),
                ride.getCarType().getSeat(),
                ride.getActualDistance(),
                ride.getActualWaitTime(),
                ride.getFare());
    }

}

package services;

import database.Enum.ECarType;
import database.Enum.EDriverStatus;
import database.Enum.ERideStatus;
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
    private FareCalculator fareCalculator = new FareCalculator();


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

    public Ride bookRide(Location pickupLocation, Location expectedDestination, ECarType eCarType, LocalDateTime expectedPickupTime) {
        double fare = fareCalculator.calculateFare(pickupLocation, expectedDestination, eCarType);
        Ride ride = new Ride(ClientService.currentClient, pickupLocation, expectedDestination, fare, expectedPickupTime);
        currentRide = ride;
        waitingRides.add(ride);
        return ride;
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
        updateRideStatus(getById(rideId), ERideStatus.CANCELLED);
    }

    public void getRideDetail() {
        System.out.println(currentRide.toString());
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

    public void finishRide(Location actualDestination, int waitTime) {
        double fare = fareCalculator.calculateFare(currentRide.getPickupLocation(),
                actualDestination, currentRide.getDriver().getCar().getCarType(), waitTime);
        currentRide.setFare(fare);
        currentRide.setActualDestination(actualDestination);
        currentRide.setWaitTime(waitTime);
        currentRide.setEndTime(getDateTimeNow());
        currentRide.setStatus(ERideStatus.COMPLETED);
        update(currentRide);
        currentRide = null;
        DriverService.currentDriver.setCurrentRide(null);
        DriverService.currentDriver.setDriverStatus(EDriverStatus.AVAILABLE);
    }

    public static void printAvailableRides() {
        System.out.println("ID\tName\t\tPickup\t\t\tDestination\t\tFare");
        System.out.println("----------------------------------------------------------");
        autoDeclineRide();
        for (Ride ride : waitingRides.stream().filter(e -> getDuration(getDateTimeNow(), e.getExpectedPickupTime()) < 15).collect(Collectors.toList())) {
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


}

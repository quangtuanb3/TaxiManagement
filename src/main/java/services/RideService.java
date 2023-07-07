package services;

import database.Enum.ECarType;
import database.Enum.EDriverStatus;
import database.Enum.ERideStatus;
import models.Driver;
import models.Location;
import models.Ride;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class RideService implements BasicCRUD<Ride> {
    public static List<Ride> listRides;
    static List<Driver> availableDriver = DriverService.listDrivers.stream().filter(Driver::isAvailable).collect(Collectors.toList());
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

    public Ride bookRide(Location pickupLocation, Location expectedDestination, ECarType eCarType) {
        double fare = fareCalculator.calculateFare(pickupLocation, expectedDestination, eCarType);
        Ride ride = new Ride(ClientService.currentClient, pickupLocation, expectedDestination, fare);
        currentRide = ride;
        waitingRides.add(ride);
        listRides.add(ride);
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
            waitingRides = waitingRides.stream().filter(e -> e.getId()!=rideId).collect(Collectors.toList());
        } else {
            DriverService.currentDriver.setDriverStatus(EDriverStatus.UNAVAILABLE);
        }
    }

    public void assignRideToDriver(Ride ride, Driver driver) {
        driver.setCurrentRide(ride);
        driver.setDriverStatus(EDriverStatus.ON_TRIP);
        ride.setStatus(ERideStatus.CONFIRMED);
        ride.setDriver(driver);
    }

    public static void printAvailableRides() {
        System.out.println("ID\tName\tPickup\t\tDestination\tFare");
        System.out.println("-----------------------------------------------");

        for (Ride ride : waitingRides) {
            System.out.printf("%s\t%s\t%s\t%s\t%.2f%n",
                    ride.getId(),
                    ride.getClient().getName(),
                    ride.getPickupLocation().getAddress(),
                    ride.getExpectedDestination().getAddress(),
                    ride.getFare());
        }
    }

}

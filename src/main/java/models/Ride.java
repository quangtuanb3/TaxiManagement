package models;

import database.Enum.ERideStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

import static services.RideService.listRides;

public class Ride implements Serializable {
    private int id;
    private Client client;
    private Driver driver;
    private Location pickupLocation;
    private Location actualDestination;
    private Location expectedDestination;
    private Location driverLocation;
    private Double fare;
    private ERideStatus status;
    private LocalDateTime expectedPickupTime;
    private LocalDateTime startTime;
    private LocalDateTime confirmedTime;
    private LocalDateTime endTime;
    private int waitTime;


    public Ride(int id, Client client, Driver driver, Location pickupLocation, Location actualDestination, Location expectedDestination, Location driverLocation, Double fare, ERideStatus status, LocalDateTime expectedPickupTime, LocalDateTime startTime, LocalDateTime confirmedTime, LocalDateTime endTime, int waitTime) {
        this.id = id;
        this.client = client;
        this.driver = driver;
        this.pickupLocation = pickupLocation;
        this.actualDestination = actualDestination;
        this.expectedDestination = expectedDestination;
        this.driverLocation = driverLocation;
        this.fare = fare;
        this.status = status;
        this.expectedPickupTime = expectedPickupTime;
        this.startTime = startTime;
        this.confirmedTime = confirmedTime;
        this.endTime = endTime;
        this.waitTime = waitTime;
    }

    public Ride() {
    }

    public Driver getDriver() {
        return driver;
    }

    public Location getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(Location driverLocation) {
        this.driverLocation = driverLocation;
    }

    public Ride(Client client, Location pickupLocation, Location expectedDestination, Double fare, LocalDateTime expectedPickupTime) {
        this.id = getNextId();
        this.client = client;
        this.pickupLocation = pickupLocation;
        this.expectedDestination = expectedDestination;
        this.fare = fare;
        this.status = ERideStatus.WAITING;
        this.startTime = expectedPickupTime;
    }

    // Getters and setters for the properties
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", clients=" + client +
                ", driver =" + driver +
                ", car=" + driver.getCar().getLicensePlate() +
                ", pickupLocation=" + pickupLocation +
                ", actualDestination=" + actualDestination +
                ", expectedDestination=" + expectedDestination +
                ", DriverLocation=" + driverLocation +
                ", fare=" + fare +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", wait=" + waitTime +
                '}';
    }

    public static void printRide(Ride ride) {
        if (ride == null) {
            System.out.println("There is no ride");
            return;
        }
        System.out.printf("%s\t%s\t\t%s\t%s\t\t%.2f%n",
                ride.getId(),
                ride.getClient().getName(),
                ride.getPickupLocation().getAddress(),
                ride.getExpectedDestination().getAddress(),
                ride.getFare());
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(LocalDateTime confirmedTime) {
        this.confirmedTime = confirmedTime;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Location getActualDestination() {
        return actualDestination;
    }

    public void setActualDestination(Location actualDestination) {
        this.actualDestination = actualDestination;
    }

    public Location getExpectedDestination() {
        return expectedDestination;
    }

    public void setExpectedDestination(Location expectedDestination) {
        this.expectedDestination = expectedDestination;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public LocalDateTime getExpectedPickupTime() {
        return expectedPickupTime;
    }

    public void setExpectedPickupTime(LocalDateTime expectedPickupTime) {
        this.expectedPickupTime = expectedPickupTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public Double getFare() {
        return fare;
    }

    public boolean isWaitApprove() {
        return this.status.equals(ERideStatus.WAITING);
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public ERideStatus getStatus() {
        return status;
    }

    public void setStatus(ERideStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public static int getNextId() {
        int max = 0;
        if (listRides != null) {
            for (Ride ride : listRides) {
                if (ride.getId() > max) {
                    max = ride.getId();
                }
            }
        }
        return max + 1;
    }
}

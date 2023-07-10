package models;

import DAO.Enum.ECarType;
import DAO.Enum.ERideStatus;
import utils.DistanceCalculator;

import java.io.Serializable;
import java.time.LocalDateTime;

import static services.RideService.fareCalculator;
import static services.RideService.listRides;

public class Ride implements Serializable {
    private int id;
    private Client client;
    private Driver driver;
    private ECarType eCarType;
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
    private Integer expectedWaitTime;
    private Integer actualWaitTime;


    public Ride(int id, Client client, Driver driver, ECarType eCarType, Location pickupLocation, Location actualDestination, Location expectedDestination, Location driverLocation, Double fare, ERideStatus status, LocalDateTime expectedPickupTime, LocalDateTime startTime, LocalDateTime confirmedTime, LocalDateTime endTime, Integer expectedWaitTime, Integer actualWaitTime) {
        this.id = id;
        this.client = client;
        this.driver = driver;
        this.eCarType = eCarType;
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
        this.expectedWaitTime = expectedWaitTime;
        this.actualWaitTime = actualWaitTime;
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

    public Ride(Client client, ECarType eCarType, Location pickupLocation, Location expectedDestination, Double fare, LocalDateTime expectedPickupTime, int expectedWaitTime) {
        this.id = getNextId();
        this.client = client;
        this.pickupLocation = pickupLocation;
        this.expectedDestination = expectedDestination;
        this.fare = fare;
        this.status = ERideStatus.WAITING;
        this.expectedPickupTime = expectedPickupTime;
        this.expectedWaitTime = expectedWaitTime;
        this.eCarType = eCarType;
    }

    // Getters and setters for the properties
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id:" + id +"\n"+
                "Client Name: \t \t \t " + client.getName() + "\n"+
                "Driver Name: \t\t\t" + (driver == null ? "waiting" : driver.getName()) +"\n"+
                "Pickup Location: \t\t\t" + pickupLocation.getAddress() +"\n"+
                "Destination: \t\t" + (actualDestination == null ? expectedDestination.getAddress() : actualDestination.getAddress()) +"\n"+
                "Distance \t\t\t " + (fareCalculator.getActualDistance() == null ? fareCalculator.getExpectedDistance() : fareCalculator.getActualDistance()) +"\n"+
                "Wait time \t\t\t" + (actualWaitTime == null ? expectedWaitTime : actualWaitTime) +"\n"+
                "Fare \t\t\t" + fare;
    }


    public void setId(int id) {
        this.id = id;
    }

    public ECarType getCarType() {
        return eCarType;
    }

    public Double getExpectedDistance() {
        return DistanceCalculator.getDistance(pickupLocation.getAddress(), expectedDestination.getAddress());
    }

    public Double getActualDistance() {
        return DistanceCalculator.getDistance(pickupLocation.getAddress(), actualDestination.getAddress());
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

    public int getExpectedWaitTime() {
        return expectedWaitTime;
    }

    public void setExpectedWaitTime(int expectedWaitTime) {
        this.expectedWaitTime = expectedWaitTime;
    }

    public int getActualWaitTime() {
        return actualWaitTime;
    }

    public void setActualWaitTime(int actualWaitTime) {
        this.actualWaitTime = actualWaitTime;
    }

    public LocalDateTime getExpectedPickupTime() {
        return expectedPickupTime;
    }

    public void setExpectedPickupTime(LocalDateTime expectedPickupTime) {
        this.expectedPickupTime = expectedPickupTime;
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

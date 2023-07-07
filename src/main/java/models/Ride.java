package models;

import database.Enum.ERideStatus;

import java.sql.Date;
import java.sql.Time;

public class Ride {
    static int currentId;
    private int id;
    private Client client;
    private Driver driver;
    private Location pickupLocation;
    private Location actualDestination;
    private Location expectedDestination;
    private Location driverLocation;

    private double fare;
    private ERideStatus status;
    private Date startTime;
    private Date endTime;
    private Time waitTime;


    public Ride(int id, Client client, Driver driver, Location pickupLocation, Location actualDestination, Location expectedDestination, Location driverLocation, double fare, ERideStatus status, Date startTime, Date endTime, Time wait) {
        this.id = id;
        this.client = client;
        this.driver = driver;
        this.pickupLocation = pickupLocation;
        this.actualDestination = actualDestination;
        this.expectedDestination = expectedDestination;
        this.driverLocation = driverLocation;
        this.fare = fare;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.waitTime = wait;
    }

    public Ride() {
    }


    public Ride(Client client, Location pickupLocation, Location expectedDestination, double fare) {
        this.client = client;
        this.pickupLocation = pickupLocation;
        this.expectedDestination = expectedDestination;
        this.fare = fare;
        this.status = ERideStatus.WAITING;
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

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public Time getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Time waitTime) {
        this.waitTime = waitTime;
    }

    public double getFare() {
        return fare;
    }

    public boolean isWaitApprove() {
        return this.status.equals(ERideStatus.WAITING);
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public ERideStatus getStatus() {
        return status;
    }

    public void setStatus(ERideStatus status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}

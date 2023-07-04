package models;

import java.sql.Time;
import java.time.LocalDateTime;

public class Ride {
    static int currentId;
    private int id;
    private Client client;
    private Car car;
    private Driver driver;
    private Location pickupLocation;
    private Location actualDestination;
    private Location expectedDestination;
    private Location driverLocation;

    private double fare;
    private RideStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Time wait;


    public Ride(int id, Client client, Car car, Driver driver, Location pickupLocation, Location actualDestination, Location expectedDestination, Location driverLocation, double fare, RideStatus status, LocalDateTime startTime, LocalDateTime endTime, Time wait) {
        this.id = id;
        this.client = client;
        this.car = car;
        this.driver = driver;
        this.pickupLocation = pickupLocation;
        this.actualDestination = actualDestination;
        this.expectedDestination = expectedDestination;
        this.driverLocation = driverLocation;
        this.fare = fare;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.wait = wait;
    }

    public Ride() {
    }



    public Ride(Client client, Location pickupLocation, Location expectedDestination, double fare) {
        this.client = client;
        this.pickupLocation = pickupLocation;
        this.expectedDestination = expectedDestination;
        this.fare = fare;
    }

    // Getters and setters for the properties
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", clients.csv=" + client +
                ", driver =" + driver +
                ", car=" + car.getLicensePlate() +
                ", pickupLocation=" + pickupLocation +
                ", actualDestination=" + actualDestination +
                ", expectedDestination=" + expectedDestination +
                ", DriverLocation=" + driverLocation +
                ", fare=" + fare +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", wait=" + wait +
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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

    public Time getWait() {
        return wait;
    }

    public void setWait(Time wait) {
        this.wait = wait;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
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

}

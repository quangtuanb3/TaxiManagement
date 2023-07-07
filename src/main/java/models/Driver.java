package models;

import database.Enum.EAuth;
import database.Enum.EDriverStatus;

import java.io.Serializable;
import java.util.List;


public class Driver extends Person implements Serializable {
    private List<Ride> listRides;
    private Ride currentRide;
    private Car car;
    private EDriverStatus driverStatus = EDriverStatus.AVAILABLE;
    private int salary;
    private Location location;
    private String accountStatus = "inactive";

    static final EAuth auth = EAuth.DRIVER;

    public Driver() {
    }

    public Driver(String name, String email, String password, String phoneNumber, Car car, int salary, String status, List<Ride> listRides) {
        super(name, email, password, phoneNumber);
        this.car = car;
        this.salary = salary;
        this.accountStatus = status;
        this.listRides = listRides;
    }

    public Driver(String name, String email, String password, String phoneNumber, Car car, int salary, EDriverStatus driverStatus, String accountStatus) {
        super(name, email, password, phoneNumber);
        this.car = car;
        this.salary = salary;
        this.driverStatus = driverStatus;
        this.accountStatus = accountStatus;
    }

    public Driver(String name, String email, String password, String phoneNumber, String accountStatus) {
        super(name, email, password, phoneNumber);
        this.accountStatus = accountStatus;
    }


    public List<Ride> getListRides() {
        return listRides;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    // Getters and setters for the properties
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public boolean isAvailable() {
        return this.driverStatus.equals(EDriverStatus.AVAILABLE);
    }

    public void setCurrentRide(Ride currentRide) {
        this.currentRide = currentRide;
    }

    public void setListRides(List<Ride> listRides) {
        this.listRides = listRides;
    }

    public Ride getCurrentRide() {
        return currentRide;
    }

    public EDriverStatus getDriverStatus() {
        return driverStatus;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDriverStatus(EDriverStatus status) {
        this.driverStatus = status;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }


    @Override
    public String toString() {
        return "id: " + getId() +
                ", Name: " + getName() +
                ", Email: " + getEmail() +
                ", Car: " + car.getId() +
                ", " + getAccountStatus() +
                ", salary: " + salary +
                ", accountStatus: " + accountStatus + '\n';
    }
}

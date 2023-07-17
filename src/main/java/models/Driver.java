package models;

import Data.Enum.EAccountStatus;
import Data.Enum.EAuth;
import Data.Enum.EDriverStatus;
import utils.AppUtils;

import java.io.Serializable;

import static services.DriverService.listDrivers;


public class Driver extends Person implements Serializable {
    private Ride currentRide;
    private Car car;
    private EDriverStatus driverStatus = EDriverStatus.AVAILABLE;
    private int salary;
    private Location location;
    private EAccountStatus accountStatus;

    static final EAuth auth = EAuth.DRIVER;

    public Driver() {
    }

    public Driver(String name, String email, String password, String phoneNumber, Car car, int salary, EAccountStatus status) {
        super(name, email, password, phoneNumber, EAuth.DRIVER);
        this.setId(getNextId());
        this.car = car;
        this.salary = salary;
        this.accountStatus = status;

    }

    public Driver(String name, String email, String password, String phoneNumber, Car car, int salary, EDriverStatus driverStatus, EAccountStatus accountStatus, Location location) {
        super(name, email, password, phoneNumber, EAuth.DRIVER);
        this.car = car;
        this.salary = salary;
        this.driverStatus = driverStatus;
        this.accountStatus = accountStatus;
    }

    public Driver(String name, String email, String password, String phoneNumber, EAccountStatus accountStatus) {
        super(name, email, password, phoneNumber, EAuth.DRIVER);
        this.accountStatus = accountStatus;
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

    public EAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(EAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String toTableRow() {
        return String.format("| %-4s | %-20s | %-26s | %-10s | %-13s | %-16s | %-15s |%n",
                getId(),
                getName(),
                getEmail(),
                car != null ? car.getLicensePlate() : "Waiting",
                AppUtils.convertPrice(salary),
                driverStatus,
                accountStatus);
    }

    public static int getNextId() {
        if (listDrivers != null) {
            return listDrivers.stream()
                    .mapToInt(Driver::getId)
                    .max()
                    .orElse(0) + 1;
        }
        return 1;
    }

}

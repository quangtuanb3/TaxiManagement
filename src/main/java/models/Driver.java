package models;

import DAO.Enum.EAccountStatus;
import DAO.Enum.EAuth;
import DAO.Enum.EDriverStatus;
import services.DriverService;
import services.RideService;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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
        super(name, email, password, phoneNumber);
        this.setId(getNextId());
        this.car = car;
        this.salary = salary;
        this.accountStatus = status;

    }

    public Driver(String name, String email, String password, String phoneNumber, Car car, int salary, EDriverStatus driverStatus, EAccountStatus accountStatus) {
        super(name, email, password, phoneNumber);
        this.car = car;
        this.salary = salary;
        this.driverStatus = driverStatus;
        this.accountStatus = accountStatus;
    }

    public Driver(String name, String email, String password, String phoneNumber, EAccountStatus accountStatus) {
        super(name, email, password, phoneNumber);
        this.accountStatus = accountStatus;
    }


    public List<Ride> getListRides() {
        return RideService.listRides.stream().filter(e -> e.getDriver().getId() == DriverService.currentDriver.getId()).collect(Collectors.toList());
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

    public static int getNextId() {
        int max = 0;
        if (listDrivers != null) {
            for (Driver driver : listDrivers) {
                if (driver.getId() > max) {
                    max = driver.getId();
                }
            }
        }
        return max + 1;
    }
}

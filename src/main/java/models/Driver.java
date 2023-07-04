package models;

import java.util.List;


public class Driver extends Person {
    private List<Ride> listRides;
    private Car car;
    private boolean available;
    private int salary;
    private String accountStatus = "inactive";
    static final int auth = 2;



    public Driver(String name, String email, String password, String phoneNumber, Car car, boolean available, int salary, String status, List<Ride> listRides) {
        super(name, email, password, phoneNumber);
        this.car = car;
        this.available = available;
        this.salary = salary;
        this.accountStatus = status;
        this.listRides = listRides;
    }

    public Driver() {
    }

    public Driver(String name, String email, String password, String phoneNumber, String status) {
        super(name, email, password, phoneNumber);
    }

    public Driver(String name, String email, String password, String phoneNumber) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setPhoneNumber(phoneNumber);
        setAccountStatus("active");
    }

    public Driver(List<Ride> listRides, Car car, boolean available, int salary, String accountStatus) {
        this.listRides = listRides;
        this.car = car;
        this.available = available;
        this.salary = salary;
        this.accountStatus = accountStatus;
    }

    public Driver(String name, String email, String password, String phoneNumber, List<Ride> listRides, Car car, boolean available, int salary, String accountStatus) {
        super(name, email, password, phoneNumber);
        this.listRides = listRides;
        this.car = car;
        this.available = available;
        this.salary = salary;
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
        return available;
    }


    public void setAvailable(boolean available) {
        this.available = available;
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
                ", " + (available ? " Available" : " Unavailable") +
                ", salary: " + salary +
                ", accountStatus: " + accountStatus + '\n';
    }
}

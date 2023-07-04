package models;

import java.sql.Date;


public class Car  {
    static int currentId = 0;
    private int id;
    private String model;
    private String licensePlate;
    private int seats;
    private int price;
    private int waitPrice;
    private Date registrationExpiryDate;
    private Date insuranceExpiryDate;
    Driver driver = new Driver();
    String status = "Using";

    public Car() {

    }

    public Car(String model, String licensePlate, int seats, int price, int waitPrice, String registrationExpiryDate, String insuranceExpiryDate, Driver driver, String status) {
        this.id = ++currentId;
        this.model = model;
        this.licensePlate = licensePlate;
        this.seats = seats;
        this.price = price;
        this.waitPrice = waitPrice;
        this.insuranceExpiryDate = Date.valueOf(insuranceExpiryDate);
        this.registrationExpiryDate = Date.valueOf(registrationExpiryDate);
        this.driver = driver;
        this.status = status;
    }

    public Car(String model, String licensePlate, int seats, int price, int waitPrice, String registrationExpiryDate, String insuranceExpiryDate) {
        this.id = ++currentId;
        this.model = model;
        this.licensePlate = licensePlate;
        this.seats = seats;
        this.price = price;
        this.waitPrice = waitPrice;
        this.insuranceExpiryDate = Date.valueOf(insuranceExpiryDate);
        this.registrationExpiryDate = Date.valueOf(registrationExpiryDate);
    }

    public Car(int id, String model, String licensePlate, int seats, int price, int waitPrice, String registrationExpiryDate, String insuranceExpiryDate) {
        this.id = id;
        this.model = model;
        this.licensePlate = licensePlate;
        this.seats = seats;
        this.price = price;
        this.waitPrice = waitPrice;
        this.insuranceExpiryDate = Date.valueOf(insuranceExpiryDate);
        this.registrationExpiryDate = Date.valueOf(registrationExpiryDate);
    }

    public static int getCurrentId() {
        return currentId;
    }

    public static void setCurrentId(int currentId) {
        Car.currentId = currentId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWaitPrice() {
        return waitPrice;
    }

    public void setWaitPrice(int waitPrice) {
        this.waitPrice = waitPrice;
    }

//    @Override
//    public String serializeData() {
//        return id + "," +
//                model + "," +
//                licensePlate + "," +
//                seats + "," +
//                price + "," +
//                waitPrice + "," +
//                registrationExpiryDate + "," +
//                insuranceExpiryDate + "," +
//                driver.getId() + "," +
//                driver.getName() + "," +
//                status;
//
//    }


    @Override
    public String toString() {
        return
                "id=" + id +
                        ", model='" + model + '\'' +
                        ", licensePlate='" + licensePlate + '\'' +
                        ", seats=" + seats +
                        ", price=" + price +
                        ", waitPrice=" + waitPrice +
                        ", registrationExpiryDate='" + registrationExpiryDate + '\'' +
                        ", insuranceExpiryDate='" + insuranceExpiryDate + '\''
                        +
                        ", driver Id='" + this.driver.getId() + '\'' +
                        ", driver Name ='" + this.driver.getName() + '\n'
                ;
    }

    // Getters and setters for the properties
    public int getId() {
        return id;
    }

    public Date getRegistrationExpiryDate() {
        return registrationExpiryDate;
    }

    public void setRegistrationExpiryDate(Date registrationExpiryDate) {
        this.registrationExpiryDate = registrationExpiryDate;
    }

    public Date getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }

    public void setInsuranceExpiryDate(Date insuranceExpiryDate) {
        this.insuranceExpiryDate = insuranceExpiryDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}

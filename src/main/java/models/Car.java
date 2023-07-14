package models;

import Data.Enum.ECarStatus;
import Data.Enum.ECarType;

import java.io.Serializable;
import java.time.LocalDate;

import static services.CarService.listCars;


public class Car implements Serializable {
    private int id;
    private String model;
    private String licensePlate;
    private ECarType carType;
    private LocalDate registrationExpiryDate;
    private LocalDate insuranceExpiryDate;
    Driver driver = new Driver();
    ECarStatus status = ECarStatus.USING;

    public Car() {

    }

    public Car(String model, String licensePlate, ECarType carType, LocalDate registrationExpiryDate, LocalDate insuranceExpiryDate, Driver driver, ECarStatus status) {
        this.model = model;
        this.licensePlate = licensePlate;
        this.carType = carType;
        this.insuranceExpiryDate = insuranceExpiryDate;
        this.registrationExpiryDate = registrationExpiryDate;
        this.driver = driver;
        this.status = status;
    }

    public Car(String model, String licensePlate, ECarType carType, LocalDate registrationExpiryDate, LocalDate insuranceExpiryDate) {
        this.model = model;
        this.licensePlate = licensePlate;
        this.carType = carType;
        this.insuranceExpiryDate = insuranceExpiryDate;
        this.registrationExpiryDate = registrationExpiryDate;
    }
    public String toTableRow() {
        return String.format("| %-3s | %-10s | %-13s | %-5s | %-10s | %-14s | %-14s | %-10s | %-20s | %-17s | %-14s | %-16s | %-9s |%n",
                id,
                model,
                licensePlate,
                carType.getSeat(),
                carType.getOpenPrice(),
                carType.getPriceUnder30(),
                carType.getPriceUpper30(),
                carType.getWaitPrice(),
                registrationExpiryDate,
                insuranceExpiryDate,
                (this.driver == null ? "Waiting Assign" : this.driver.getId()),
                (this.driver == null ? "Waiting Assign" : this.driver.getName()),
                getStatus().getCarStatus()
                )
        ;
    }

    // Getters and setters for the properties

    public LocalDate getRegistrationExpiryDate() {
        return registrationExpiryDate;
    }

    public void setRegistrationExpiryDate(LocalDate registrationExpiryDate) {
        this.registrationExpiryDate = registrationExpiryDate;
    }

    public LocalDate getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }

    public void setInsuranceExpiryDate(LocalDate insuranceExpiryDate) {
        this.insuranceExpiryDate = insuranceExpiryDate;
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

    public ECarType getCarType() {
        return carType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public static int getNextId() {
        int max = 0;
        if (listCars != null) {
            for (Car car : listCars) {
                if (car.getId() > max) {
                    max = car.getId();
                }
            }
        }
        return max + 1;
    }

    public void setCarType(ECarType carType) {
        this.carType = carType;
    }

    public Driver getDriver() {
        return driver;
    }

    public ECarStatus getStatus() {
        return status;
    }

    public void setStatus(ECarStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void printDetail() {
        System.out.println("ID\tLicense Plate\t\tSeat\tOpen Price\tPrice Under 30\tPrice Upper 30\tInsurance Expiry\tRegistration Expiry");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%s\t%s\t%d\t%d\t\t%d\t\t%d\t\t%s\t%s\n",
                id, licensePlate,
                carType.getSeat(),
                carType.getOpenPrice(),
                carType.getPriceUnder30(),
                carType.getPriceUpper30(),
                insuranceExpiryDate,
                registrationExpiryDate);
    }


}

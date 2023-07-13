package models;

import Data.Enum.ECarStatus;
import Data.Enum.ECarType;

import java.io.Serializable;
import java.time.LocalDate;

import static services.CarService.listCars;


public class Car implements Serializable {
    private  int id;
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


    @Override
    public String toString() {
        return
                "id=" + id +
                        ", model='" + model + '\'' +
                        ", licensePlate='" + licensePlate + '\'' +
                        ", seats=" + carType.getSeat() + '\'' +
                        ", openPrice=" + carType.getOpenPrice() + '\'' +
                        ", priceUnder30 =" + carType.getPriceUnder30() + '\'' +
                        ", priceUpper30 =" + carType.getPriceUpper30() + '\'' +
                        ", waitPrice=" + carType.getWaitPrice() + '\'' +
                        ", registrationExpiryDate='" + registrationExpiryDate + '\'' +
                        ", insuranceExpiryDate='" + insuranceExpiryDate + '\'' +
                        ", driver Id='" + this.driver.getId() + '\'' +
                        ", driver Name ='" + this.driver.getName() + '\n'
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

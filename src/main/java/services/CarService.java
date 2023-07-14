package services;

import Data.Enum.ECarStatus;
import Data.Enum.EDriverStatus;
import Data.Enum.EPath;
import models.Car;
import models.Driver;
import utils.AppUtils;
import utils.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CarService implements BasicCRUD<Car> {
    public static List<Car> listCars;
    private static int nextId;
    private static CarService instance;

    public static CarService getInstance() {
        if (instance == null) {
            instance = new CarService();
        }
        return instance;
    }

    static {
        listCars = new ArrayList<>((List<Car>) Serializable.deserialize(EPath.CARS.getFilePath()));
        nextId = AppUtils.getNextId(listCars.stream().map(Car::getId).toList());
    }

    public CarService() {
    }

    public static boolean recall(Car car) {
        if (car.getDriver() != null) {
            car.setDriver(null);
            car.setStatus(ECarStatus.WAITING_ASSIGN);
            save();
            return true;
        }
        return false;
    }


    @Override
    public Car getById(String str) {
        int carID = AppUtils.getInt(str);
        Car car = listCars.stream().filter(e -> e.getId() == carID).findFirst().orElse(null);
        if (car == null) {
            System.out.println("Car not found. Please try again!");
            getById(str);
        }
        return car;
    }

    @Override
    public Car getById(int id) {
        return listCars.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }


    @Override
    public List<Car> getAll() {
        return listCars;
    }

    @Override
    public boolean create(Car car) {
        if (listCars.stream().anyMatch(e -> e.getLicensePlate().equals(car.getLicensePlate()))) {
            return false;
        }
        car.setId(CarService.nextId);
        listCars.add(car);
        save();
        return true;
    }

    @Override
    public void update(Car car) {
        listCars.stream()
                .filter(e -> e.getId() == car.getId())
                .findFirst()
                .ifPresent(e -> {
                    int i = listCars.indexOf(e);
                    listCars.set(i, car);
                    save();
                });
    }


    public static void save() {
        Serializable.serialize(listCars, EPath.CARS.getFilePath());
    }

    @Override
    public boolean isExist(int carId) {
        Car car = listCars.stream()
                .filter(e -> Objects.equals(e.getId(), carId))
                .findFirst()
                .orElse(null);
        return car != null;
    }

    @Override
    public void delete(int carId) {
        listCars = listCars.stream()
                .filter(e -> !Objects.equals(e.getId(), carId))
                .toList();
        save();
    }

    public void printAvailableCar() {
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("| ID  | Model      | License Plate | Seats | Open Price | Price Under 30 | Price Upper 30 | Wait Price | Registration Expired | Insurance Expired | Driver ID      | Driver Name      | Car status  |\n");
        tableBuilder.append("|-----|------------|---------------|-------|------------|----------------|----------------|------------|----------------------|-------------------|----------------|------------------|-------------|\n");
        for (Car car : listCars.stream().filter(e -> e.getStatus() != ECarStatus.USED).toList()) {
            tableBuilder.append(car.toTableRow());
        }
        System.out.println(tableBuilder);
    }

    public void printAll() {
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("| ID  | Model      | License Plate | Seats | Open Price | Price Under 30 | Price Upper 30 | Wait Price | Registration Expired | Insurance Expired | Driver ID      | Driver Name      | Car status  |\n");
        tableBuilder.append("|-----|------------|---------------|-------|------------|----------------|----------------|------------|----------------------|-------------------|----------------|------------------|-------------|\n");
        for (Car car : listCars) {
            tableBuilder.append(car.toTableRow());
        }
        System.out.println(tableBuilder);
    }

    public boolean assignCarToDriver(Car car, Driver driver) {
        if (car == null || driver == null) {
            return false;
        }
        Car oldCar = driver.getCar();
        if (oldCar != null) {
            System.out.printf("Driver %s (Id %s) is already assigned to a car with license plate %s.\n", driver.getName(), driver.getId(), driver.getCar().getLicensePlate());
            int choice = AppUtils.getIntWithBound("Press 1 to continue or 0 to go back to the previous menu: ", 0, 1);
            if (choice == 1) {
                oldCar.setDriver(null);
                oldCar.setStatus(ECarStatus.WAITING_ASSIGN);

                car.setDriver(driver);
                car.setStatus(ECarStatus.USING);
                driver.setCar(car);

                update(car);
                update(oldCar);
                DriverService.getInstance().update(driver);
                return true;
            }
        } else {
            car.setDriver(driver);
            car.setStatus(ECarStatus.USING);
            driver.setCar(car);
            driver.setDriverStatus(EDriverStatus.AVAILABLE);
            update(car);
            DriverService.getInstance().update(driver);
            return true;

        }
        return false;
    }

}

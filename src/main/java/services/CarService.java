package services;

import Data.Enum.ECarStatus;
import Data.Enum.EPath;
import models.Car;
import models.Driver;
import utils.AppUtils;
import utils.Serializable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class CarService implements BasicCRUD<Car> {
    public static List<Car> listCars;

    static {
        listCars = (List<Car>) Serializable.deserialize(EPath.CARS.getFilePath());
        int nextId = AppUtils.getNextId(listCars.stream().map(Car::getId).collect(Collectors.toList()));
    }

    public CarService() {
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
                .collect(Collectors.toList());
        save();
    }

    public void printAvailableCar() {
        for (Car car : listCars.stream().filter(e -> e.getStatus() != ECarStatus.USED).collect(Collectors.toList())) {
            System.out.println(car.toString());
        }
    }

    public void printAll(){
        for (Car car : listCars) {
            System.out.println(car.toString());
        }
    }

    public boolean assignCarToDriver(Car car, Driver driver) {
        Driver oldDriver = car.getDriver();
        if (oldDriver != null && oldDriver != driver) {
            System.out.printf("This car has already assigned to %s (Driver Id: %d) \n", oldDriver.getName(), oldDriver.getId());
            int choice = AppUtils.getIntWithBound("Press 1 to continue or 0 to back preview menu", 0, 1);
            if (choice == 1) {
                oldDriver.setCar(null);
                car.setDriver(driver);
                update(car);
                DriverService.getInstance().update(oldDriver);
                return true;
            }
        }
        return false;
    }
}

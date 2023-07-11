package services;

import DAO.Enum.EPath;
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
    public Car getById(int id) {
        Car foundCar = new Car();
        for (Car car : listCars) {
            if (car.getId() == id) {
                foundCar = car;
            }
        }
        return foundCar;
    }

    @Override
    public Car getObjById(List<Car> cars, String str) {
        int carID = AppUtils.getInt(str);
        return cars.stream().filter(e -> e.getId() == carID).findFirst().orElse(null);
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

    public void print() {
        for (Car car : listCars) {
            System.out.println(car.toString());
        }
    }

    public void assignCarToDriver(Car car, Driver driver) {
        car.setDriver(driver);
        update(car);
    }
}

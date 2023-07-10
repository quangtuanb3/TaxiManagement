package services;

import DAO.Enum.EPath;
import models.Car;
import models.Driver;
import utils.Serializable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class CarService implements BasicCRUD<Car> {
    public static List<Car> listCars;


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
    public List<Car> getAll() {
        return listCars;
    }

    @Override
    public void create(Car car)  {
        listCars.add(car);
        save();
    }

    @Override
    public void update(Car car) {
        for (int i = 0; i < listCars.size(); i++) {
            Car existingCar = listCars.get(i);
            if (existingCar.getId() == car.getId()) {
                listCars.set(i, car);
                break;
            }
        }
    }

    public static void save() {
        Serializable.serialize(listCars, EPath.CARS.getFilePath());
    }

    @Override
    public boolean isExist(List<Car> listCars, int carId) {
        Car car = listCars.stream()
                .filter(e -> Objects.equals(e.getId(), carId))
                .findFirst()
                .orElse(null);
        return car != null;
    }

    @Override
    public void delete(int carId)  {
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

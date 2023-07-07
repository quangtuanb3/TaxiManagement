package services;

import database.Enum.EPath;
import models.Driver;
import utils.Serializable;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class DriverService implements BasicCRUD<Driver> {
    public static List<Driver> listDrivers;
    public static Driver currentDriver;
    public DriverService() {
    }

    @Override
    public Driver getById(int id) {
        Driver foundDriver = new Driver();
        for (Driver driver : listDrivers) {
            if (driver.getId() == id) {
                foundDriver = driver;
            }
        }
        return foundDriver;
    }

    @Override
    public List<Driver> getAll() {
        return listDrivers;
    }

    @Override
    public void create(Driver driver) throws IOException {
        listDrivers.add(driver);
        saveDrives();
    }

    @Override
    public void update(Driver driver) {
        for (int i = 0; i < listDrivers.size(); i++) {
            Driver existingDriver = listDrivers.get(i);
            if (existingDriver.getId() == driver.getId()) {
                listDrivers.set(i, driver);
                break;
            }
        }
    }
    public static void saveDrives() {
        Serializable.serialize(listDrivers, EPath.DRIVERS.getFilePath());
    }
    public static Driver getByEmail(String email) {
        return listDrivers.stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
    @Override
    public boolean isExist(int driverId) {
        Driver driver = listDrivers.stream()
                .filter(e -> Objects.equals(e.getId(), driverId))
                .findFirst()
                .orElse(null);
        return driver != null;
    }

    @Override
    public void delete(int driverId) {
        listDrivers = listDrivers.stream()
                .filter(e -> !Objects.equals(e.getId(), driverId))
                .collect(Collectors.toList());
    }


    public void print() {
        for (Driver driver : listDrivers) {
            System.out.println(driver.toString());
        }
    }


}

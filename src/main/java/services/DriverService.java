package services;

import DAO.Enum.EPath;
import models.Driver;
import models.Ride;
import utils.AppUtils;
import utils.Serializable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class DriverService implements BasicCRUD<Driver> {
    public static List<Driver> listDrivers;
    public static Driver currentDriver;
    private static DriverService instance;

    public static DriverService getInstance() {
        if (instance == null) {
            instance = new DriverService();
        }
        return instance;
    }

    static {
        listDrivers = (List<Driver>) Serializable.deserialize(EPath.DRIVERS.getFilePath());

    }

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
    public Driver getObjById(List<Driver> drivers, String str) {
        int carID = AppUtils.getInt(str);
        return drivers.stream().filter(e -> e.getId() == carID).findFirst().orElse(null);
    }

    @Override
    public List<Driver> getAll() {
        return listDrivers;
    }

    @Override
    public boolean create(Driver driver) {
        if (listDrivers.stream().anyMatch(e -> Objects.equals(e.getEmail(), driver.getEmail()))) {
            System.out.println("Email has been use");
            return false;
        }
        listDrivers.add(driver);
        save();
        return true;
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

    public static void save() {
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

    public static void printRideHistory() {
        if (DriverService.currentDriver != null) {
            for (Ride ride : RideService.listRides.stream().filter(e -> e.getDriver().equals(DriverService.currentDriver)).collect(Collectors.toList())) {
                System.out.println(ride.toString());
            }

        }

    }

}

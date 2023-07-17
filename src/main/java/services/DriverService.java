package services;

import Data.Enum.EPath;
import models.Driver;
import models.Ride;
import utils.AppUtils;
import utils.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
        listDrivers = loadData();
    }

    public DriverService() {
    }


    @Override
    public Driver getById(String str) {
        int driverId = AppUtils.getInt(str);
        Driver driver = listDrivers.stream().filter(e -> e.getId() == driverId).findFirst().orElse(null);
        if (driver == null) {
            System.out.println("Driver not found. Please try again!");
            getById(str);
        }
        return driver;
    }

    @Override
    public Driver getById(int id) {
        return listDrivers.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
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
        save();
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
                .toList();
    }


    public void print() {
        if (listDrivers.size() == 0) {
            System.out.println("There is no Driver");
        }
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("| ID   | Name                 | Email                      | Car        | Salary        | Driver Status    | Account Status  |\n");
        tableBuilder.append("|------|----------------------|----------------------------|------------|---------------|------------------|-----------------|\n");

        for (Driver driver : listDrivers) {
            tableBuilder.append(driver.toTableRow());
        }
        System.out.println(tableBuilder);
    }

    public static void printRideHistory() {
        if (DriverService.currentDriver == null) {
            System.out.println("There is no ride");
            return;
        }
        List<Ride> history = RideService.listRides.stream().filter(e -> Objects.equals(e.getDriver(), DriverService.currentDriver)).toList();
        if (history.size() == 0) {
            System.out.println("There is no ride");
            return;
        } else if (history.get(0) == null) {
            System.out.println("There is no ride");
            return;
        }
        for (Ride ride : history) {
            System.out.println(ride.toTableRow());
        }

    }


    public static List<Driver> loadData() {
        return new ArrayList<>((List<Driver>) Serializable.deserialize(EPath.DRIVERS.getFilePath()));
    }
}

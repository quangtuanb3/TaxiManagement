package services;

import DAO.Enum.EPath;
import models.Driver;
import models.Ride;
import services.authServices.LoginService;
import utils.Serializable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class DriverService implements BasicCRUD<Driver> {
    public static List<Driver> listDrivers;
    public static Driver currentDriver;

    static {
        listDrivers = (List<Driver>) Serializable.deserialize(EPath.DRIVERS.getFilePath());
        if (LoginService.currentUser instanceof Driver) {
            currentDriver = (Driver) LoginService.currentUser;
        }
    }

    public DriverService() {
        }

        @Override
        public Driver getById ( int id){
            Driver foundDriver = new Driver();
            for (Driver driver : listDrivers) {
                if (driver.getId() == id) {
                    foundDriver = driver;
                }
            }
            return foundDriver;
        }

        @Override
        public List<Driver> getAll () {
            return listDrivers;
        }

        @Override
        public void create (Driver driver){
            listDrivers.add(driver);
            saveDrives();
        }

        @Override
        public void update (Driver driver){
            for (int i = 0; i < listDrivers.size(); i++) {
                Driver existingDriver = listDrivers.get(i);
                if (existingDriver.getId() == driver.getId()) {
                    listDrivers.set(i, driver);
                    break;
                }
            }
        }

        public static void saveDrives () {
            Serializable.serialize(listDrivers, EPath.DRIVERS.getFilePath());
        }

        public static Driver getByEmail (String email){
            return listDrivers.stream()
                    .filter(e -> e.getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public boolean isExist ( int driverId){
            Driver driver = listDrivers.stream()
                    .filter(e -> Objects.equals(e.getId(), driverId))
                    .findFirst()
                    .orElse(null);
            return driver != null;
        }

        @Override
        public void delete ( int driverId){
            listDrivers = listDrivers.stream()
                    .filter(e -> !Objects.equals(e.getId(), driverId))
                    .collect(Collectors.toList());
        }


        public void print () {
            for (Driver driver : listDrivers) {
                System.out.println(driver.toString());
            }
        }

        public static void printRideHistory () {
            if (LoginService.currentUser != null) {
                for (Ride ride : RideService.listRides.stream().filter(e -> e.getDriver().equals(LoginService.currentUser)).collect(Collectors.toList())) {
                    System.out.println(ride.toString());
                }

            }

        }

    }

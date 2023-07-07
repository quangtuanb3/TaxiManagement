package database;

import database.Enum.EAccountStatus;
import database.Enum.ECarType;
import database.Enum.EDriverStatus;
import database.Enum.EPath;
import models.*;
import services.*;
import utils.AppUtils;
import utils.Serializable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Startup {

    public static void init() {
        if (CarService.listCars == null) {
            initCars();
        }
        if (DriverService.listDrivers == null) {
            initDrivers();
        }
        if (ClientService.listClients == null) {
            initClients();
        }
        if (ManagerService.listManagers == null) {
            initManager();
        }
        if (RideService.listRides == null) {
            initRide();
        }
        CarService.listCars.get(0).setDriver(DriverService.listDrivers.get(0));
        CarService.listCars.get(1).setDriver(DriverService.listDrivers.get(1));
        Serializable.serialize(CarService.listCars, AppUtils.getDirectoryPath(EPath.CARS.toString()));
    }

    private static void initCars() {
        Car car1 = new Car("A584", "75A-19283", ECarType.FOUR, Date.valueOf("2024-03-20"), Date.valueOf("2024-03-20"), null, "Available");
        Car car2 = new Car("A484", "75A-79542", ECarType.SEVEN, Date.valueOf("2024-04-13"), Date.valueOf("2024-04-13"), null, "Available");
        List<Car> listCars = new ArrayList<>();
        listCars.add(car1);
        listCars.add(car2);
        CarService.listCars = listCars;
        Serializable.serialize(listCars, AppUtils.getDirectoryPath(EPath.CARS.toString()));
    }

    private static void initDrivers() {

        Driver driver1 = new Driver("Trong Ty", "tythiwin@gmail.com",
                "123456", "09314141444", CarService.listCars.get(0),
                15000000, EDriverStatus.AVAILABLE, EAccountStatus.ACTIVE.getAccountStatus());
        Driver driver2 = new Driver("Dat zero", "datzero9@gmail.com",
                "123456", "078654123", CarService.listCars.get(1),
                14000000, EDriverStatus.AVAILABLE, EAccountStatus.ACTIVE.getAccountStatus());
        List<Driver> listDrivers = new ArrayList<>();
        listDrivers.add(driver1);
        listDrivers.add(driver2);
        DriverService.listDrivers = listDrivers;
        Serializable.serialize(listDrivers, AppUtils.getDirectoryPath(EPath.DRIVERS.toString()));
    }

    private static void initClients() {
        Client client1 = new Client("Anh Tuan", "anhtuan@gmail.com", "123456",
                "122356", null, null);
        Client client2 = new Client("Si Phuc", "siphuc@gmail.com", "123456",
                "122356", null, null);
        List<Client> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);
        ClientService.listClients = clientList;
        Serializable.serialize(clientList, AppUtils.getDirectoryPath(EPath.CLIENTS.toString()));


    }

    private static void initManager() {
        Manager manager = new Manager("Quang Tuan", "tuan@gmail.com", "123456", "099999999");
        List<Manager> listManagers = new ArrayList<>();
        listManagers.add(manager);
        ManagerService.listManagers = listManagers;
        Serializable.serialize(listManagers, AppUtils.getDirectoryPath(EPath.MANAGERS.toString()));
    }

    private static void initRide() {


        Ride ride1 = new Ride(1, ClientService.listClients.get(0), null,
                new Location("153 Phan Bội Châu, phường Trường An, Huế, Thừa Thiên Huế"), null,
                new Location("Laguna Lăng Cô, Phú Lộc, Thừa Thiên Huế, Việt Nam"), null, 700000,
                database.Enum.ERideStatus.WAITING, null, null, null);
        Ride ride2 = new Ride(2, ClientService.listClients.get(1), null,
                new Location("Ga Huế, Phường Đúc, Huế, Thừa Thiên Huế"), null,
                new Location("Sân Bay Phú Bài, Hương Thủy, Thừa Thiên Huế, Việt Nam"), null, 500000,
                database.Enum.ERideStatus.WAITING, null, null, null);

        List<Ride> listRides = new ArrayList<>();
        listRides.add(ride1);
        listRides.add(ride2);
        RideService.listRides = listRides;
        RideService.waitingRides = listRides;
        Serializable.serialize(listRides, AppUtils.getDirectoryPath(EPath.RIDES.toString()));
    }

}



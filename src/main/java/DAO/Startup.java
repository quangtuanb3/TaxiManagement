package DAO;

import DAO.Enum.EAccountStatus;
import DAO.Enum.ECarStatus;
import DAO.Enum.ECarType;
import DAO.Enum.EDriverStatus;
import models.*;
import services.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static utils.AppUtils.parseDateTime;

public class Startup {

    public static void init() {
        if (CarService.listCars.isEmpty()) {
            initCars();
        }
        if (ManagerService.listManagers.isEmpty()) {
            initManager();
        }
        if (DriverService.listDrivers.isEmpty()) {
            initDrivers();
        }
        if (ClientService.listClients.isEmpty()) {
            initClients();
        }
        if (RideService.listRides.isEmpty()) {
            initRide();
        }
        CarService.listCars.get(0).setDriver(DriverService.listDrivers.get(0));
        CarService.listCars.get(1).setDriver(DriverService.listDrivers.get(1));

        CarService.save();
    }

    private static void initCars() {
        Car car1 = new Car("A584", "75A-19283", ECarType.FOUR, Date.valueOf("2024-03-20"), Date.valueOf("2024-03-20"), null, ECarStatus.USING);
        Car car2 = new Car("A484", "75A-79542", ECarType.SEVEN, Date.valueOf("2024-04-13"), Date.valueOf("2024-04-13"), null, ECarStatus.USING);
        List<Car> listCars = new ArrayList<>();
        listCars.add(car1);
        listCars.add(car2);
        CarService.listCars = listCars;
        CarService.save();
    }

    private static void initDrivers() {

        Driver driver1 = new Driver("Trong Ty", "tythiwin@gmail.com",
                "1234", "09314141444", CarService.listCars.get(0),
                15000000, EDriverStatus.AVAILABLE, EAccountStatus.ACTIVE);
        Driver driver2 = new Driver("Dat zero", "datzero9@gmail.com",
                "1234", "078654123", CarService.listCars.get(1),
                14000000, EDriverStatus.AVAILABLE, EAccountStatus.ACTIVE);
        List<Driver> listDrivers = new ArrayList<>();
        listDrivers.add(driver1);
        listDrivers.add(driver2);
        DriverService.listDrivers = listDrivers;
        DriverService.save();
    }

    private static void initClients() {
        Client client1 = new Client("Anh Tuan", "anhtuan@gmail.com", "1234",
                "12238584");
        Client client2 = new Client("Si Phuc", "siphuc@gmail.com", "1234",
                "12235858");
        List<Client> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);
        ClientService.listClients = clientList;
        ClientService.save();

    }

    private static void initManager() {
        Manager manager = new Manager("Quang Tuan", "tuan@gmail.com", "1234", "099999999");
        List<Manager> listManagers = new ArrayList<>();
        listManagers.add(manager);
        ManagerService.listManagers = listManagers;
        ManagerService.save();

    }

    private static void initRide() {
        Ride ride1 = new Ride(1, ClientService.listClients.get(0), null, ECarType.FOUR,
                new Location("153 Phan Bội Châu, phường Trường An, Huế, Thừa Thiên Huế"), null,
                new Location("Laguna Lăng Cô, Phú Lộc, Thừa Thiên Huế, Việt Nam"), null, null,
                DAO.Enum.ERideStatus.WAITING, parseDateTime("2023-07-11 11:00:00"), null, null, null, 0, null);
        Ride ride2 = new Ride(2, ClientService.listClients.get(1), null, ECarType.SEVEN,
                new Location("Ga Huế, Phường Đúc, Huế, Thừa Thiên Huế"), null,
                new Location("Sân Bay Phú Bài, Hương Thủy, Thừa Thiên Huế, Việt Nam"), null, null,
                DAO.Enum.ERideStatus.WAITING, parseDateTime("2023-07-11 11:00:00"), null, null, null, 0, null);

        List<Ride> listRides = new ArrayList<>();
        listRides.add(ride1);
        listRides.add(ride2);
        RideService.listRides = listRides;
        RideService.waitingRides = listRides;

        RideService.save();

    }

}



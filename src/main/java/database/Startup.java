package database;

import models.Car;
import models.Client;
import models.Driver;
import models.Manager;
import services.CarService;
import services.ClientService;
import services.DriverService;
import services.ManagerService;
import utils.Serializable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Startup {
    public static void init() throws IOException {

        Car car1 = new Car("A584", "75A-19283", 7, 20000, 20000, "2024-03-20", "2024-03-20", null, "Available");
        Car car2 = new Car("A484", "75A-79542", 4, 20000, 20000, "2024-04-10", "2024-04-10", null, "Available");

        Client client1 = new Client("Anh Tuan", "anhtuan@gmail.com", "123456", "122356", null, null);
        Client client2 = new Client("Si Phuc", "siphuc@gmail.com", "123456", "122356", null, null);

        Driver driver1 = new Driver("Trong Ty", "tythiwin@gmail.com", "123456", "09314141444", null, car1, true, 15000000, "active");
        Driver driver2 = new Driver("Dat zero", "datzero9@gmail.com", "123456", "078654123", null, car2, true, 14000000, "active");

        car1.setDriver(driver1);
        car2.setDriver(driver2);

        Manager manager = new Manager("Quang Tuan", "tuan@gmail.com", "123456", "099999999");


        List<Car> carList = new ArrayList<>();
        carList.add(car1);
        carList.add(car2);
        Serializable.serialize(carList, Path.CARS.toString());

        List<Driver> driverList = new ArrayList<>();
        driverList.add(driver1);
        driverList.add(driver2);
        Serializable.serialize(driverList, Path.DRIVERS.toString());


        List<Manager> managerList = new ArrayList<>();
        managerList.add(manager);
        Serializable.serialize(managerList, Path.MANAGERS.toString());

        List<Client> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);
        Serializable.serialize(clientList, Path.CLIENTS.toString());
    }

    public static void loadClients() throws IOException, ClassNotFoundException {
        ClientService.listClients =(List<Client>) Serializable.deserialize(Path.CLIENTS.toString());
    }

    public static void loadCars() throws IOException, ClassNotFoundException {
        CarService.listCars = (List<Car>) Serializable.deserialize(Path.CARS.toString());
    }

    public static void loadManagers() throws IOException, ClassNotFoundException {
        ManagerService.listManagers =(List<Manager>) Serializable.deserialize(Path.MANAGERS.toString());
    }

    public static void loadDrivers() throws IOException, ClassNotFoundException {
        DriverService.listDrivers =(List<Driver>) Serializable.deserialize(Path.DRIVERS.toString());
    }

    static {
        try {
            // Load listClients after calling init()
            init();
            loadClients();
            loadCars();
            loadDrivers();
            loadManagers();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
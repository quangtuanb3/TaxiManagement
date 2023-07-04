package views;

import models.Car;
import models.Driver;
import services.CarService;
import utils.AppUtils;

import java.io.IOException;

import static services.CarService.saveCars;
import static services.DriverService.saveDrives;
import static views.DriverManagerView.driverService;
import static views.MangerView.managerMenu;

public class CarManagerView {
     static CarService carService = new CarService();
    public static void carMenu() throws IOException {
        int choice;
        do {
            System.out.println("Cars manager menu");
            System.out.println("1. List Cars");
            System.out.println("2. Add new car");
            System.out.println("3. Remove car");
            System.out.println("4. Assign car");
            System.out.println("5. Get car's detail");
            System.out.println("0. Back to main menu");
            choice = AppUtils.getIntWithBound("Input choice: ", 0, 5);
            switch (choice) {
                case 1:
                    carService.print();
                    break;
                case 2:
                    carService.create(addCar());
                    break;
                case 3:
                    removeCar();
                    break;
                case 4:
                    assignCar("Cars > Assign Car");
                    break;
                case 5:
                    getCarDetail();
                    break;
                case 0:
                    managerMenu();
                    break;
            }
        }
        while (choice != 0);
    }

    public static Car addCar() {
        String model = AppUtils.getString("Input model:");
        String licensePlate = AppUtils.getString("Input licensePlate: ");
        int seats = AppUtils.getInt("Input seats: ");
        int price = AppUtils.getInt("Input price: ");
        int waitPrice = AppUtils.getInt("Input wait price: ");
        String registrationExpiryDate = AppUtils.getString("Input registration expiry date: ");
        String insuranceExpiryDate = AppUtils.getString("Input insurance expiry date: ");
        return new Car(model, licensePlate, seats, price, waitPrice, registrationExpiryDate, insuranceExpiryDate);
    }

    static void getCarDetail() {
        System.out.println("Get Car's detail");
        int carId = AppUtils.getInt("Input Car id: ");
        if (!carService.isExist(carId)) {
            System.out.printf("Not found %d.\n", carId);
            getCarDetail();
        }
        Car car = carService.getById(carId);
        System.out.println(car.toString());
    }

    public static void assignCar(String str) throws IOException {
        System.out.println(str);
        int driverId = AppUtils.getInt("Input Driver Id: ");
        if (driverService.isExist(driverId)) {
            Driver driver = driverService.getById(driverId);
            carService.print();
            int carId = AppUtils.getInt("Input Car Id: ");
            if (carService.isExist(carId)) {
                Car car = carService.getById(carId);
                carService.assignCarToDriver(car, driver);
                saveCars();
                saveDrives();
            }
        } else assignCar(str);
    }

    static void removeCar() throws IOException {
        carService.print();
        int carId = AppUtils.getInt("Input car id to remove: ");
        if (!carService.isExist(carId)) {
            System.out.printf("Not found %d.\n", carId);
            removeCar();
        }
        carService.delete(carId);
        System.out.println("Remove car successfully!");

    }
}

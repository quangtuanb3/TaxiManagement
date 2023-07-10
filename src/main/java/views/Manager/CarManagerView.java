package views.Manager;

import DAO.Enum.ECarType;
import models.Car;
import models.Driver;
import services.CarService;
import services.DriverService;
import utils.AppUtils;

import java.sql.Date;

import static views.Manager.DriverManagerView.driverService;
import static views.Manager.MangerView.managerMenu;

public class CarManagerView {
    static CarService carService = new CarService();

    public static void carMenu() {
        try {
            int choice;
            do {
                System.out.println("Cars manager menu");
                System.out.println("1. List Cars");
                System.out.println("2. Add new Car");
                System.out.println("3. Remove Car");
                System.out.println("4. Assign Car");
                System.out.println("5. Get Car's detail");
                System.out.println("6. Update Car's Information");
                System.out.println("0. Back to main menu");
                choice = AppUtils.getIntWithBound("Input choice: ", 0, 6);
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
                    case 6:
                        updateCarUi();
                        break;
                    case 0:
                        managerMenu();
                        break;
                }
            }
            while (choice != 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void updateCarUi() {
        try {
            System.out.println("Update Car's Information: ");
            carService.print();
            int carId = AppUtils.getInt("Input Car's Id: ");
            if (carService.isExist(CarService.listCars, carId)) {
                Car car = carService.getById(carId);
                System.out.println("1. Edit Model");
                System.out.println("2. Edit license plate number");
                System.out.println("3. Edit insurance expiry date");
                System.out.println("4. Edit registration expiry date");
                System.out.println("0. Back to Cars manager menu ");
                int choice = AppUtils.getIntWithBound("Input choice: ", 0, 4);
                switch (choice) {
                    case 1:
                        String model = AppUtils.getString("Input new Model: ");
                        car.setModel(model);
                        break;
                    case 2:
                        String license = AppUtils.getString("Input new license plate: ");
                        car.setLicensePlate(license);
                        break;
                    case 3:
                        Date insuranceExpired = AppUtils.getDate("Input new insurance expired : ");
                        car.setInsuranceExpiryDate(insuranceExpired);
                        break;
                    case 4:
                        Date registrationExpired = AppUtils.getDate("Input new registration expired: ");
                        car.setRegistrationExpiryDate(registrationExpired);
                        break;
                    case 0:
                        carMenu();
                        break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static Car addCar() {
        String model = AppUtils.getString("Input model:");
        String licensePlate = AppUtils.getString("Input licensePlate: ");
        ECarType carType = AppUtils.getIntWithBound("Input type of car: (1. Four Seats/ 2. Seven Seats)", 1, 2) == 1 ? ECarType.FOUR : ECarType.SEVEN;
        Date registrationExpiryDate = AppUtils.getDate("Input registration expiry date: ");
        Date insuranceExpiryDate = AppUtils.getDate("Input insurance expiry date: ");
        return new Car(model, licensePlate, carType, registrationExpiryDate, insuranceExpiryDate);
    }

    static void getCarDetail() {
        System.out.println("Get Car's detail");
        int carId = AppUtils.getInt("Input Car id: ");
        if (!carService.isExist(CarService.listCars, carId)) {
            System.out.printf("Not found %d.\n", carId);
            getCarDetail();
        }
        Car car = carService.getById(carId);
        System.out.println(car.toString());
    }

    public static void assignCar(String str) {
        System.out.println(str);
        driverService.print();
        int driverId = AppUtils.getInt("Input Driver Id: ");
        if (driverService.isExist(DriverService.listDrivers, driverId)) {
            Driver driver = driverService.getById(driverId);
            carService.print();
            int carId = AppUtils.getInt("Input Car Id: ");
            if (carService.isExist(CarService.listCars, carId)) {
                Car car = carService.getById(carId);
                carService.assignCarToDriver(car, driver);
            }
        } else assignCar(str);

    }

    static void removeCar() {
        try {
            carService.print();
            int carId = AppUtils.getInt("Input car id to remove: ");
            if (!carService.isExist(CarService.listCars, carId)) {
                System.out.printf("Not found %d.\n", carId);
                removeCar();
            }
            carService.delete(carId);
            System.out.println("Remove car successfully!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

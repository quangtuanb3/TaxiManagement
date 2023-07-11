package views.Manager;

import DAO.Enum.ECarType;
import models.Car;
import models.Driver;
import services.CarService;
import utils.AppUtils;
import utils.ListView;

import java.sql.Date;

import static views.Manager.DriverManagerView.driverService;
import static views.Manager.MangerView.managerMenu;

public class CarManagerView {
    static CarService carService = new CarService();

    public static void carMenu() {
        ListView.printMenu(ListView.carManagerMenuList);
        int choice;
        choice = AppUtils.getIntWithBound("Input choice: ", 0, ListView.carManagerMenuList.size() - 2);
        switch (choice) {
            case 1:
                carService.print();
                break;
            case 2:
                addCarUi();
                break;
            case 3:
                removeCar();
                break;
            case 4:
                assignCar();
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

    private static void updateCarUi() {
        try {
            System.out.println("Update Car's Information: ");
            carService.print();
            int carId = AppUtils.getInt("Input Car's Id: ");
            if (carService.isExist(carId)) {
                Car car = carService.getById(carId);
              ListView.printMenu(ListView.updateCarMenuList);
                int choice = AppUtils.getIntWithBound("Input choice: ", 0, ListView.updateCarMenuList.size()-1);
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
                carService.update(car);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void addCarUi() {
        String model = AppUtils.getString("Input model:");
        String licensePlate = AppUtils.getString("Input licensePlate: ");
        ECarType carType = AppUtils.getIntWithBound("Input type of car: (1. Four Seats/ 2. Seven Seats)", 1, 2) == 1 ? ECarType.FOUR : ECarType.SEVEN;
        Date registrationExpiryDate = AppUtils.getDate("Input registration expiry date: ");
        Date insuranceExpiryDate = AppUtils.getDate("Input insurance expiry date: ");
        Car car = new Car(model, licensePlate, carType, registrationExpiryDate, insuranceExpiryDate);
        carService.create(car);
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

    public static void assignCar() {
        System.out.println("Assign car");
        driverService.print();
        int driverId = AppUtils.getInt("Input Driver Id: ");
        if (driverService.isExist(driverId)) {
            Driver driver = driverService.getById(driverId);
            carService.print();
            int carId = AppUtils.getInt("Input Car Id: ");
            if (carService.isExist(carId)) {
                Car car = carService.getById(carId);
                carService.assignCarToDriver(car, driver);
            } else assignCar();
        } else assignCar();
    }

    static void removeCar() {
        try {
            carService.print();
            int carId = AppUtils.getInt("Input car id to remove: ");
            if (!carService.isExist(carId)) {
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

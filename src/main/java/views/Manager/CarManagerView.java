package views.Manager;

import Data.Enum.ECarStatus;
import Data.Enum.ECarType;
import Data.Enum.EDriverStatus;
import models.Car;
import models.Driver;
import services.CarService;
import services.DriverService;
import utils.AppUtils;
import utils.ListView;

import java.time.LocalDate;

import static views.Manager.MangerView.managerMenu;

public class CarManagerView {

    public static void carMenu() {
        ListView.printMenu(ListView.carManagerMenuList);
        int choice;
        choice = AppUtils.getIntWithBound("Input choice: ", 0, ListView.carManagerMenuList.size() - 2);
        switch (choice) {
            case 1 -> CarService.getInstance().printAll();
            case 2 -> addCarUI();
            case 3 -> removeCar();
            case 4 -> recallCarUI();
            case 5 -> assignCar();
            case 6 -> getCarDetail();
            case 7 -> updateCarUi();
            case 0 -> managerMenu();
        }
        carMenu();
    }

    private static void recallCarUI() {
        System.out.println("Recall Car Menu ");
        CarService.getInstance().printAvailableCar();
        Car car = CarService.getInstance().getById("Input Car Id: ");
        System.out.printf("You are recalling car %s from Driver %s. \n", car.getLicensePlate(), car.getDriver().getName());
        int choice = AppUtils.getIntWithBound("Input 1 to continue or 0 to cancel", 0, 1);
        if (choice == 1) {
            if (CarService.recall(car)) {
                System.out.println("Recall car successfully");
                return;
            }
            System.out.println("Unable to recall car. Please try again");
            recallCarUI();
        } else {
            carMenu();
        }
    }

    private static void updateCarUi() {
        try {
            System.out.println("Update Car's Information: ");
            CarService.getInstance().printAvailableCar();
            int carId = AppUtils.getInt("Input Car's Id: ");
            if (CarService.getInstance().isExist(carId)) {
                Car car = CarService.getInstance().getById(carId);
                ListView.printMenu(ListView.updateCarMenuList);
                int choice = AppUtils.getIntWithBound("Input choice: ", 0, ListView.updateCarMenuList.size() - 2);
                switch (choice) {
                    case 1 -> {
                        String model = AppUtils.getString("Input new Model: ");
                        car.setModel(model);
                    }
                    case 2 -> {
                        String license = AppUtils.getString("Input new license plate: ");
                        car.setLicensePlate(license);
                    }
                    case 3 -> {
                        LocalDate insuranceExpired = AppUtils.getDate("Input new insurance expired : ");
                        car.setInsuranceExpiryDate(insuranceExpired);
                    }
                    case 4 -> {
                        LocalDate registrationExpired = AppUtils.getDate("Input new registration expired: ");
                        car.setRegistrationExpiryDate(registrationExpired);
                    }

                    case 0 -> carMenu();
                }
                CarService.getInstance().update(car);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addCarUI() {
        String model = AppUtils.getString("Input model:");
        String licensePlate = AppUtils.getString("Input licensePlate: ");
        ECarType carType = AppUtils.getIntWithBound("Input type of car: (1. Four Seats/ 2. Seven Seats)", 1, 2) == 1 ? ECarType.FOUR : ECarType.SEVEN;
        LocalDate registrationExpiryDate = AppUtils.getDate("Input registration expiry date: ");
        LocalDate insuranceExpiryDate = AppUtils.getDate("Input insurance expiry date: ");
        Car car = new Car(model, licensePlate, carType, registrationExpiryDate, insuranceExpiryDate);
        CarService.getInstance().create(car);
    }

    static void getCarDetail() {
        System.out.println("Get Car's detail");
        int carId = AppUtils.getInt("Input Car id: ");
        if (!CarService.getInstance().isExist(carId)) {
            System.out.printf("Not found %d.\n", carId);
            getCarDetail();
        }
        Car car = CarService.getInstance().getById(carId);
        System.out.println(car.toTableRow());
    }

    public static void assignCar() {
        System.out.println("Assign car menu");
        CarService.getInstance().printAvailableCar();
        Car car = CarService.getInstance().getById("Input car Id: ");
        if (!CarService.isInList(CarService.getAvailableCar(), car.getId())) {
            System.out.println("Car not found");
            int choice = AppUtils.getIntWithBound("Press 1 to continue or 0 to back preview menu", 1, 1);
            if (choice == 0) {
                carMenu();
            } else {
                assignCar();
            }
        }
        if (car.getDriver() != null) {
            System.out.printf("This car has been assigned to %s. Please recall car first \n", car.getDriver().getName());
            carMenu();
        }
        DriverService.getInstance().print();
        Driver driver;
        do {
            driver = DriverService.getInstance().getById("Input driver Id: ");
            if (!driver.isAvailable() && !driver.getDriverStatus().equals(EDriverStatus.WAITING_ASSIGN)) {
                System.out.println("This driver can be assigned car (Account is blocking/inactive or Driver is on trip)");
            }
        } while ( !driver.isAvailable() && !driver.getDriverStatus().equals(EDriverStatus.WAITING_ASSIGN));

        if (CarService.getInstance().assignCarToDriver(car, driver)) {
            System.out.println("Assign car successfully");
        } else {
            System.out.println("Assign car has been canceled");
            carMenu();
        }
    }

    static void removeCar() {
        CarService.getInstance().printAvailableCar();
        Car car = CarService.getInstance().getById("Input car id to remove:");
        car.setStatus(ECarStatus.USED);
        CarService.getInstance().update(car);
        System.out.println("Remove car successfully!");

    }
}

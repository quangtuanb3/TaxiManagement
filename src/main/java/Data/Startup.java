package Data;

import Data.Enum.*;
import models.*;
import services.*;
import utils.AppUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static services.ClientService.listClients;

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
        if (listClients.isEmpty()) {
            initClients();
        }
        if (RideService.listRides.isEmpty()) {
            initRide();
        }
        CarService.listCars.get(0).setDriver(DriverService.listDrivers.get(0));
        CarService.listCars.get(1).setDriver(DriverService.listDrivers.get(1));
        CarService.listCars.get(2).setDriver(DriverService.listDrivers.get(2));
        CarService.listCars.get(3).setDriver(DriverService.listDrivers.get(3));
        CarService.listCars.get(4).setDriver(DriverService.listDrivers.get(4));
        CarService.listCars.get(5).setDriver(DriverService.listDrivers.get(5));
        CarService.listCars.get(6).setDriver(DriverService.listDrivers.get(6));
        CarService.listCars.get(7).setDriver(DriverService.listDrivers.get(7));
        CarService.save();
    }

    private static void initCars() {
        Car car1 = new Car();
        car1.setModel("A584");
        car1.setLicensePlate("75A-19836");
        car1.setCarType(ECarType.FOUR);
        car1.setRegistrationExpiryDate(LocalDate.parse("2024-03-20"));
        car1.setInsuranceExpiryDate(LocalDate.parse("2024-03-20"));
        car1.setStatus(ECarStatus.USING);

        Car car2 = new Car();
        car2.setModel("V234");
        car2.setLicensePlate("75A-66472");
        car2.setCarType(ECarType.FOUR);
        car2.setRegistrationExpiryDate(LocalDate.parse("2023-10-20"));
        car2.setInsuranceExpiryDate(LocalDate.parse("2023-10-20"));
        car2.setStatus(ECarStatus.USING);

        Car car3 = new Car();
        car3.setModel("B664");
        car3.setLicensePlate("75A-78942");
        car3.setCarType(ECarType.FOUR);
        car3.setRegistrationExpiryDate(LocalDate.parse("2023-11-20"));
        car3.setInsuranceExpiryDate(LocalDate.parse("2023-11-20"));
        car3.setStatus(ECarStatus.USING);

        Car car4 = new Car();
        car4.setModel("B446");
        car4.setLicensePlate("75A-26647");
        car4.setCarType(ECarType.SEVEN);
        car4.setRegistrationExpiryDate(LocalDate.parse("2023-09-20"));
        car4.setInsuranceExpiryDate(LocalDate.parse("2023-09-20"));
        car4.setStatus(ECarStatus.USING);

        Car car5 = new Car();
        car5.setModel("B446");
        car5.setLicensePlate("75A-44679");
        car5.setCarType(ECarType.SEVEN);
        car5.setRegistrationExpiryDate(LocalDate.parse("2023-09-25"));
        car5.setInsuranceExpiryDate(LocalDate.parse("2023-09-25"));
        car5.setStatus(ECarStatus.USING);

        Car car6 = new Car();
        car6.setModel("A221");
        car6.setLicensePlate("75A-11233");
        car6.setCarType(ECarType.FOUR);
        car6.setRegistrationExpiryDate(LocalDate.parse("2023-09-25"));
        car6.setInsuranceExpiryDate(LocalDate.parse("2023-09-25"));
        car6.setStatus(ECarStatus.USING);

        Car car7 = new Car();
        car7.setModel("N4467");
        car7.setLicensePlate("75A-46467");
        car7.setCarType(ECarType.FOUR);
        car7.setRegistrationExpiryDate(LocalDate.parse("2023-10-25"));
        car7.setInsuranceExpiryDate(LocalDate.parse("2023-10-25"));
        car7.setStatus(ECarStatus.USING);

        Car car8 = new Car();
        car8.setModel("T4979");
        car8.setLicensePlate("75A-33654");
        car8.setCarType(ECarType.FOUR);
        car8.setRegistrationExpiryDate(LocalDate.parse("2023-11-25"));
        car8.setInsuranceExpiryDate(LocalDate.parse("2023-11-25"));
        car8.setStatus(ECarStatus.USING);

        Car car9 = new Car();
        car9.setModel("Y6462");
        car9.setLicensePlate("75A-99879");
        car9.setCarType(ECarType.SEVEN);
        car9.setRegistrationExpiryDate(LocalDate.parse("2023-12-31"));
        car9.setInsuranceExpiryDate(LocalDate.parse("2023-12-31"));
        car9.setStatus(ECarStatus.USING);

        CarService.listCars = Arrays.asList(car1, car2, car3, car4, car5, car6, car7, car8, car9);
        CarService.save();
    }

    private static void initDrivers() {

        Driver driver1 = new Driver();
        driver1.setName("Trong Ty");
        driver1.setEmail("tythiwin@gmail.com");
        driver1.setPassword("1234");
        driver1.setPhoneNumber("0934165987");
        driver1.setCar(CarService.listCars.get(0));
        driver1.setSalary(15000000);
        driver1.setDriverStatus(EDriverStatus.AVAILABLE);
        driver1.setAccountStatus(EAccountStatus.ACTIVE);
        driver1.setLocation(new Location("567 Lạc Long Quân, Thị Trấn Lăng Cô, Huyện Phú Lộc, Thừa Thiên Huế, Việt Nam"));

        Driver driver2 = new Driver();
        driver2.setName("Dat Zero");
        driver2.setEmail("datzero@gmail.com");
        driver2.setPassword("1234");
        driver2.setPhoneNumber("0788976654");
        driver2.setCar(CarService.listCars.get(1));
        driver2.setSalary(14000000);
        driver2.setDriverStatus(EDriverStatus.AVAILABLE);
        driver2.setAccountStatus(EAccountStatus.ACTIVE);
        driver2.setLocation(new Location("71 Khe Tre, Thị Trấn Khe Tre, " +
                "Huyện Nam Đông, Thừa Thiên Huế, Việt Nam"));

        Driver driver3 = new Driver();
        driver3.setName("Việt Cường");
        driver3.setEmail("vietcuong@gmail.com");
        driver3.setPassword("1234");
        driver3.setPhoneNumber("0989979989");
        driver3.setCar(CarService.listCars.get(2));
        driver3.setSalary(14000000);
        driver3.setDriverStatus(EDriverStatus.AVAILABLE);
        driver3.setAccountStatus(EAccountStatus.ACTIVE);
        driver3.setLocation(new Location("Sân Bay Phú Bài, phường Phú Bài, " +
                "Thị xã Hương Thủy, Thừa Thiên Huế, Việt Nam"));

        Driver driver4 = new Driver();
        driver4.setName("Việt Anh");
        driver4.setEmail("vietanh@gmail.com");
        driver4.setPassword("1234");
        driver4.setPhoneNumber("0966147148");
        driver4.setCar(CarService.listCars.get(3));
        driver4.setSalary(14000000);
        driver4.setDriverStatus(EDriverStatus.AVAILABLE);
        driver4.setAccountStatus(EAccountStatus.ACTIVE);
        driver4.setLocation(new Location("Nguyễn Đức Xuyên, Phú Đa, Phú Vang, Thừa Thiên Huế, Việt Nam"));

        Driver driver5 = new Driver();
        driver5.setName("Thế Anh");
        driver5.setEmail("theanh@gmail.com");
        driver5.setPassword("1234");
        driver5.setPhoneNumber("0966147148");
        driver5.setCar(CarService.listCars.get(4));
        driver5.setSalary(14000000);
        driver5.setDriverStatus(EDriverStatus.AVAILABLE);
        driver5.setAccountStatus(EAccountStatus.ACTIVE);
        driver5.setLocation(new Location("01 Đ. Nguyễn Khoa Đăng, Tứ Hạ, Hương Trà, Thừa Thiên Huế 535300, Việt Nam"));

        Driver driver6 = new Driver();
        driver6.setName("Văn Nam");
        driver6.setEmail("vannam@gmail.com");
        driver6.setPassword("1234");
        driver6.setPhoneNumber("0966147178");
        driver6.setCar(CarService.listCars.get(5));
        driver6.setSalary(14000000);
        driver6.setDriverStatus(EDriverStatus.AVAILABLE);
        driver6.setAccountStatus(EAccountStatus.ACTIVE);
        driver6.setLocation(new Location("20 Đ. Hồ Huấn Nghiệp, A Lưới, Thừa Thiên Huế, Việt Nam"));

        Driver driver7 = new Driver();
        driver7.setName("Lê Văn Vở");
        driver7.setEmail("vanvo1000nam@gmail.com");
        driver7.setPassword("1234");
        driver7.setPhoneNumber("0966147178");
        driver7.setCar(CarService.listCars.get(6));
        driver7.setSalary(14000000);
        driver7.setDriverStatus(EDriverStatus.AVAILABLE);
        driver7.setAccountStatus(EAccountStatus.ACTIVE);
        driver7.setLocation(new Location("57 Phò Trạch, TT. Phong Điền, Phong Điền, Thừa Thiên Huế 49000, Việt Nam"));

        Driver driver8 = new Driver();
        driver8.setName("Duy Thẩm");
        driver8.setEmail("duytham@gmail.com");
        driver8.setPassword("1234");
        driver8.setPhoneNumber("0580588085");
        driver8.setCar(CarService.listCars.get(7));
        driver8.setSalary(14000000);
        driver8.setDriverStatus(EDriverStatus.AVAILABLE);
        driver8.setAccountStatus(EAccountStatus.ACTIVE);
        driver8.setLocation(new Location("15 Lê Lợi, Vĩnh Ninh, Thành phố Huế, Thừa Thiên Huế, Việt Nam"));

        DriverService.listDrivers = Arrays.asList(driver1, driver2, driver3, driver4, driver5, driver6, driver7, driver8);
        DriverService.save();
    }

    private static void initClients() {
        Client client1 = new Client("Anh Tuan", "a", "1",
                "12238584");
        Client client2 = new Client("Si Phuc", "siphuc@gmail.com", "1234",
                "12235858");
        List<Client> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);
        listClients = clientList;
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
        Ride ride1 = new Ride();
        ride1.setId(1);
        ride1.setClient(listClients.get(0));
        ride1.setCarType(ECarType.FOUR);
        ride1.setPickupLocation(new Location("153 Phan Bội Châu, phường Trường An, Huế, Thừa Thiên Huế"));
        ride1.setActualDestination(new Location("Sân Bay Phú Bài, Hương Thủy, Thừa Thiên Huế, Việt Nam"));
        ride1.setExpectedDestination(new Location("20 Nguyễn Trãi, Tây Lộc, Huế, Thừa Thiên Huế"));
        ride1.setFare(200000D);
        ride1.setExpectedDistance(15.5D);
        ride1.setActualDistance(4.7D);
        ride1.setStatus(ERideStatus.COMPLETED);
        ride1.setExpectedPickupTime(AppUtils.parseDateTime("2023-07-01 10:00:00"));
        ride1.setStartTime(AppUtils.parseDateTime("2023-07-01 10:00:00"));
        ride1.setEndTime(AppUtils.parseDateTime("2023-07-01 10:30:00"));
        ride1.setConfirmedTime(AppUtils.parseDateTime("2023-07-01 10:03:00"));
        ride1.setBookTime(AppUtils.parseDateTime("2023-07-01 10:03:00"));
        ride1.setExpectedWaitTime(30);
        ride1.setActualWaitTime(40);
        ride1.setDriver(DriverService.listDrivers.get(0));

        Ride ride2 = new Ride();
        ride2.setId(2);
        ride2.setClient(listClients.get(1));
        ride2.setCarType(ECarType.FOUR);
        ride2.setPickupLocation(new Location("Ga Huế, Phường Đúc, Huế, Thừa Thiên Huế"));
        ride2.setActualDestination(new Location("Sân Bay Phú Bài, Hương Thủy, Thừa Thiên Huế, Việt Nam"));
        ride2.setExpectedDestination(new Location("Sân Bay Phú Bài, Hương Thủy, Thừa Thiên Huế, Việt Nam"));
        ride2.setFare(200000D);
        ride2.setExpectedDistance(14.5D);
        ride2.setActualDistance(14.5D);
        ride2.setStatus(ERideStatus.COMPLETED);
        ride2.setExpectedPickupTime(AppUtils.parseDateTime("2023-07-02 07:00:00"));
        ride2.setStartTime(AppUtils.parseDateTime("2023-07-02 07:12:00"));
        ride2.setEndTime(AppUtils.parseDateTime("2023-07-02 07:42:00"));
        ride2.setConfirmedTime(AppUtils.parseDateTime("2023-07-02 07:04:00"));
        ride2.setBookTime(AppUtils.parseDateTime("2023-07-02 06:58:00"));
        ride2.setExpectedWaitTime(0);
        ride2.setActualWaitTime(0);
        ride1.setDriver(DriverService.listDrivers.get(1));

        Ride ride3 = new Ride();
        ride3.setId(3);
        ride3.setClient(listClients.get(0));
        ride3.setCarType(ECarType.FOUR);
        ride3.setPickupLocation(new Location("Sân Bay Phú Bài, Hương Thủy, Thừa Thiên Huế, Việt Nam"));
        ride3.setActualDestination(new Location("102 Phạm Văn Đồng, Vỹ Dạ, Phú Vang, Thừa Thiên Huế, Việt Nam"));
        ride3.setExpectedDestination(new Location("102 Phạm Văn Đồng, Vỹ Dạ, Phú Vang, Thừa Thiên Huế, Việt Nam"));
        ride3.setFare(220000D);
        ride3.setExpectedDistance(11.5D);
        ride3.setActualDistance(11.5D);
        ride3.setStatus(ERideStatus.COMPLETED);
        ride3.setBookTime(AppUtils.parseDateTime("2023-07-12 06:58:00"));
        ride3.setExpectedPickupTime(AppUtils.parseDateTime("2023-07-12 07:13:00"));
        ride3.setConfirmedTime(AppUtils.parseDateTime("2023-07-02 07:04:00"));
        ride3.setStartTime(AppUtils.parseDateTime("2023-07-02 07:12:00"));
        ride3.setEndTime(AppUtils.parseDateTime("2023-07-02 07:32:00"));
        ride3.setExpectedWaitTime(0);
        ride3.setActualWaitTime(0);
        ride1.setDriver(DriverService.listDrivers.get(2));

        List<Ride> listRides = new ArrayList<>();
        listRides.add(ride1);
        listRides.add(ride2);
        listRides.add(ride3);
        RideService.listRides = listRides;
        RideService.waitingRides = listRides.stream().filter(e->e.getStatus().equals(ERideStatus.WAITING)).collect(Collectors.toList());

        RideService.save();
    }
}



package services;

import models.Client;
import models.Driver;

import java.io.IOException;

public class RegisterService {
    public static void register(int status, String name, String email, String password, String phoneNumber) throws IOException {
        switch (status) {
            case 1:
                Client client = new Client(name, email, password, phoneNumber);
                ClientService clientService = new ClientService();
                clientService.create(client);
                System.out.println("Your account is signed up successful!!");
                break;
            case 2:
                Driver driver = new Driver(name, email, password, phoneNumber, "not confirm");
                DriverService driverService = new DriverService();
                driverService.create(driver);
                System.out.println("Your require has been sent to Admin. Please wait to confirm");
                break;
            default:
                System.out.println("Error");
                break;
        }
    }


}

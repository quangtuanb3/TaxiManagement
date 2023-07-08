package services.authServices;

import services.ClientService;
import services.DriverService;
import services.ManagerService;
import utils.AppUtils;

import java.io.IOException;

import static views.Client.ClientView.clientMenu;
import static views.Driver.DriverView.driverMenu;
import static views.Manager.MangerView.managerMenu;

public class LoginService {
    public static boolean login() throws IOException {
        boolean loggedIn = false;
        String email = AppUtils.getString("Input username: ");
        String password = AppUtils.getString("Input password");
        if (ManagerService.getByEmail(email)!=null && ManagerService.getByEmail(email).getPassword().equals(password)) {
            loggedIn = true;
            ManagerService.currentManager = ManagerService.getByEmail(email);
            managerMenu();
        } else if (DriverService.getByEmail(email)!=null && DriverService.getByEmail(email).getPassword().equals(password)&&DriverService.getByEmail(email).getAccountStatus().equals("active") ) {
            loggedIn = true;
            DriverService.currentDriver = DriverService.getByEmail(email);
            driverMenu();
        } else if (ClientService.getByEmail(email)!=null && ClientService.getByEmail(email).getPassword().equals(password)) {
            loggedIn = true;
            ClientService.currentClient = ClientService.getByEmail(email);
            clientMenu();
        } else {
            System.out.println("Invalid account!");
        }

        return loggedIn;
    }
}

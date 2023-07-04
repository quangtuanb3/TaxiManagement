package services;

import models.CurrentUser;
import utils.AppUtils;

import java.io.IOException;

import static views.ClientView.clientMenu;
import static views.DriverView.driverMenu;
import static views.MangerView.managerMenu;

public class LoginService {
    public static boolean login() throws IOException {
        boolean loggedIn = false;
        String username = AppUtils.getString("Input username: ");
        String password = AppUtils.getString("Input password");
        if (username.equals("admin") && password.equals("1234")) {
            loggedIn = true;
            CurrentUser.currentUser = username;
            managerMenu();
        } else if (username.equals("duy@gmail.com") && password.equals("123456")) {
            loggedIn = true;
            CurrentUser.currentUser = username;
            driverMenu();
        } else if (username.equals("hoang@gmail.com") && password.equals("1234")) {
            loggedIn = true;
            CurrentUser.currentUser = username;
            clientMenu();
        }
        return loggedIn;
    }
}

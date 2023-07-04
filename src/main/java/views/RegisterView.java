package views;

import services.RegisterService;
import utils.AppUtils;

import java.io.IOException;

import static views.LoginView.loginMenu;

public class RegisterView {
    public static void register() throws IOException {
        System.out.println("Register: ");
        String name = AppUtils.getString("Input Name");
        String email = AppUtils.getString("Input Email");
        String password = AppUtils.getString("Input Password");
        String phoneNumber = AppUtils.getString("Input Phone number");
        int status = AppUtils.getInt("Input type of account (1: client/ 2: driver)");
        RegisterService.register(status, name, email, password, phoneNumber);
        loginMenu();
    }
}

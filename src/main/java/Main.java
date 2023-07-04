import database.Startup;

import java.io.IOException;

import static views.LoginView.loginMenu;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            Startup.init();
            System.out.println("Initialization completed successfully.");
            Startup.loadClients();
            Startup.loadCars();
            Startup.loadDrivers();
            Startup.loadManagers();
            System.out.println("Load data completed successfully.");
          loginMenu();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

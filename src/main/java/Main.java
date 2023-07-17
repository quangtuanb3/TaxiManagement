import static Data.Startup.init;
import static views.LoginView.loginMenu;

public class Main {
    public static void main(String[] args) {
        try {
            init();
            loginMenu();
        } catch (Exception e) {
            System.out.println("An error has occurred. We apologize for any inconvenience caused.");
            loginMenu();
        }


    }
}

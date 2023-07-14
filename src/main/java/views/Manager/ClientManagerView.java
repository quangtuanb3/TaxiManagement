package views.Manager;

import models.Client;
import services.ClientService;
import utils.AppUtils;
import utils.ListView;

import static views.Manager.MangerView.managerMenu;

public class ClientManagerView {

    public static void clientManagerMenu() {
        ListView.printMenu(ListView.clientManagerMenuList);
        int choice = AppUtils.getIntWithBound("Input choice", 0, 3);
        switch (choice) {
            case 1 -> ClientService.getInstance().print();
            case 2 -> removeClient();
            case 0 -> {
                System.out.println("Back to manager menu");
                managerMenu();
            }
        }
    }
    private static void removeClient() {
        ClientService.getInstance().print();
        Client client = ClientService.getInstance().getById("Input client id: ");
        ClientService.getInstance().delete(client.getId());
        System.out.println("Remove driver successfully!");
    }
}

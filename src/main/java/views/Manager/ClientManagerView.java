package views.Manager;

import services.ClientService;
import utils.AppUtils;

import static views.Manager.MangerView.managerMenu;

public class ClientManagerView {
    static ClientService clientService = new ClientService();

    public static void ClientManagerMenu() {
        try {
            int choice;
            do {
                System.out.println("Driver manager menu");
                System.out.println("1. List Client");
                System.out.println("2. Remove Client");
                System.out.println("3. Get Client's detail");
                System.out.println("0. Back to main menu");
                choice = AppUtils.getIntWithBound("Input choice", 0, 3);
                switch (choice) {
                    case 1:
                        clientService.print();
                        break;
                    case 2:
                        removeClient();
                        break;
                    case 3:
                        getClientDetail();
                        break;
                    case 0:
                        System.out.println("Back to manager menu");
                        managerMenu();
                        break;
                }
            } while (choice != 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void getClientDetail() {
        System.out.println("Get Client's detail: ");
        int clientId = AppUtils.getInt("Input Client id: ");
        if (!clientService.isExist(ClientService.listClients, clientId)) {
            System.out.printf("Not found %d.\n", clientId);
            getClientDetail();
        }
        System.out.println(clientService.getById(clientId).toString());
    }

    private static void removeClient() {
        clientService.print();
        int clientID = AppUtils.getInt("Input client id to remove: ");
        if (!clientService.isExist(ClientService.listClients, clientID)) {
            System.out.printf("Not found %d.\n", clientID);
            removeClient();
        }
        clientService.delete(clientID);
        System.out.println("Remove driver successfully!");
    }
}

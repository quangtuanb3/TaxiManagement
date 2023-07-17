package models;

import Data.Enum.EAuth;
import services.ClientService;

import java.io.Serializable;

public class Client extends Person implements Serializable {
    private Ride currentRide;


    public Client(String name, String email, String password, String phoneNumber) {
        super(name, email, password, phoneNumber, EAuth.CLIENT);
        super.setId(getNextId());
    }

    public Client() {
    }

    public Client(int id, String name, String email, String password, String phone) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setPhoneNumber(phone);

    }

    public String toTableRow() {
        return String.format("| %-4s | %-20s | %-25s | %-13s | %-15s |\n",
                super.getId(), super.getName(), super.getEmail(), super.getPassword().getPasscode(), super.getPhoneNumber());
    }

    public void setCurrentRide(Ride ride) {
        this.currentRide = ride;
    }

    public Ride getCurrentRide() {
        return currentRide;
    }
    public static int getNextId() {
        if (ClientService.listClients != null) {
            return ClientService.listClients.stream()
                    .mapToInt(Client::getId)
                    .max()
                    .orElse(0) + 1;
        }
        return 1;
    }

}

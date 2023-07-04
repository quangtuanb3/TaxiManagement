package models;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person {
    static int currentId = 0;
    private int id;
    private Ride currentRide;
    private List<Ride> listRides = new ArrayList<>();
    static final int auth = 1;

    public Client(String name, String email, String password, String phoneNumber) {
        super(name, email, password, phoneNumber);
        this.id = ++currentId;
    }

    public Client() {
    }

    public Client(int id, String name, String email, String password, String phone) {
        this.id = id;
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setPhoneNumber(phone);

    }

    public Client(int id, Ride ride) {
        this.id = id;
        this.currentRide = ride;
    }

    public Client(String name, String email, String password, String phoneNumber, List<Ride> listRides, Ride currentRide) {
        super(name, email, password, phoneNumber);
        this.id = ++currentId;
        this.currentRide = currentRide;
        this.listRides = listRides;
    }

//    public String serializeData() {
//        return id + "," +
//                getName() + "," +
//                getEmail() + "," +
//                getPassword() + "," +
//                getPhoneNumber();
//    }
}

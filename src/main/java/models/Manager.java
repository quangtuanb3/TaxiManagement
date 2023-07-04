package models;

public class Manager extends Person {
    static final int auth = 3;

    public Manager() {
    }

    public Manager(String name, String email, String password, String phoneNumber) {
        super(name, email, password, phoneNumber);
    }


}

package models;

import java.io.Serializable;

public class Manager extends Person implements Serializable {
    static final int auth = 3;

    public Manager() {
    }

    public Manager(String name, String email, String password, String phoneNumber) {
        super(name, email, password, phoneNumber);
    }


}

package models;

import Data.Enum.EAuth;

import java.io.Serializable;

public class Manager extends Person implements Serializable {
    static final int auth = 3;

    public Manager() {
    }

    public Manager(String name, String email, String password, String phoneNumber) {
        super(name, email, password, phoneNumber, EAuth.ADMIN);
    }

    @Override
    public String toString() {
        return "Manager{} " + super.toString();
    }
}

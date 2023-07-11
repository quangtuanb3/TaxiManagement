package models;

import DAO.Enum.EAuth;

import java.io.Serializable;

public class Client extends Person implements Serializable {
    static final String auth = EAuth.CLIENT.getAuth();

    public Client(String name, String email, String password, String phoneNumber) {
        super(name, email, password, phoneNumber);
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

    @Override
    public String toString() {
        return super.getId() + " " +
                super.getEmail() + " " +
                super.getName();
    }
}

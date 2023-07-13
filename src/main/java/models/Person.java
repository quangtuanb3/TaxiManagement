package models;

import Data.Enum.EAuth;
import utils.AppUtils;

import java.io.Serializable;

public abstract class Person implements Serializable {
    //    private static final long serialVersionUID = 3089966L;
    private int id;
    private String name;
    private String email;
    private Password password;
    private String phoneNumber;
    private EAuth eAuth;

    public Person() {

    }

    public Person(String name, String email, String password, String phoneNumber, EAuth eAuth) {
        this.name = name;
        this.email = email;
        this.password = AppUtils.hashPassword(password);
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters for the properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = AppUtils.hashPassword(password);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password.getPasscode() + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

package services;

import models.Manager;

import java.util.List;

public class ManagerService {
    public static List<Manager> listManagers;
    public static Manager currentManager;

    public ManagerService() {
    }
    public static Manager getByEmail(String email) {
        return listManagers.stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

}

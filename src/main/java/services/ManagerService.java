package services;

import DAO.Enum.EPath;
import models.Manager;
import utils.Serializable;

import java.util.List;

public class ManagerService {
    public static List<Manager> listManagers;
    public static Manager currentManager;
    private static ManagerService instance;

    public static ManagerService getInstance() {
        if (instance == null) {
            instance = new ManagerService();
        }
        return instance;
    }

    static {
        listManagers = (List<Manager>) Serializable.deserialize(EPath.MANAGERS.getFilePath());
           }


    public ManagerService() {
    }

    public static Manager getByEmail(String email) {
        return listManagers.stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public static void save() {
        Serializable.serialize(listManagers, EPath.MANAGERS.getFilePath());
    }
}

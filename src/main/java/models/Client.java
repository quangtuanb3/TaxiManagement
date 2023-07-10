package models;

import DAO.Enum.EAuth;

import java.io.Serializable;

import static services.ClientService.listClients;

public class Client extends Person implements Serializable {
    static final String auth = EAuth.CLIENT.getAuth();

    public Client(String name, String email, String password, String phoneNumber) {
        super(name, email, password, phoneNumber);
        this.setId(getNextId());
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

    public static int getNextId() {
        int max = 0;
        if(listClients!=null){
            for (Client client : listClients) {
                if (client.getId() > max) {
                    max = client.getId();
                }
            }
        }
        return max + 1;
    }

}

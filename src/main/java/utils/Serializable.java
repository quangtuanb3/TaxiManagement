package utils;

import java.io.*;
import java.util.ArrayList;

public class Serializable {
    public static Object deserialize(String fileName) {
        Object obj = new ArrayList<>();
        try {
            if (!new File(fileName).exists()) {
                return new ArrayList<>();
            }
            FileInputStream fis = new FileInputStream(fileName);
            var ois = new ObjectInputStream(fis);
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Errors File: " + e.getMessage());
        }
        return obj;
    }

    public static void serialize(Object obj, String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            try (FileOutputStream fos = new FileOutputStream(file);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(obj);
            }
        } catch (IOException e) {
            System.out.println("Errors File: " + e.getMessage());


        }
    }


}


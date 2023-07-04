package utils;

import java.io.*;

public class Serializable {
    // deserialize to Object from given file
    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    // serialize the given object and save it to file
    public static void serialize(Object obj, String fileName)
            throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Failed to delete existing file: " + fileName);
            }
        }
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        for (Object t : obj) {
//            oos.writeObject(t);
//        }
        fos.close();
    }


//    public List<T> deserialize(String fileName) throws IOException,
//            ClassNotFoundException {
//        FileInputStream fis = new FileInputStream(fileName);
//        ObjectInputStream ois = new ObjectInputStream(fis);
//        List<T> objs = new ArrayList<>();
//        try {
//            while (true) {
//                Object obj = ois.readObject();
//                objs.add((T) obj);
//            }
//        } catch (Exception e) {
//
//        }
//        ois.close();
//        return objs;
//    }
//
//    // serialize the given object and save it to file
//    public void serialize(List<T> obj, String fileName)
//            throws IOException {
//        FileOutputStream fos = new FileOutputStream(fileName);
//        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        for (Object t : obj) {
//            oos.writeObject(t);
//        }
//        fos.close();
//    }


}


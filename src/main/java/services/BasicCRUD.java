package services;

import java.io.IOException;
import java.util.List;

public interface BasicCRUD<T> {
    T getById(int id);

    List<T> getAll();

    void create(T obj) throws IOException;

    void update(T obj);

    void delete(int id) throws IOException;

    boolean isExist(int id);

    void print();


}

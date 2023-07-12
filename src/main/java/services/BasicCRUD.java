package services;

import java.util.List;

public interface BasicCRUD<T> {
    T getById(String str);

    //    T getObjById(List<T> tList, String str);
    T getById(int id);

    List<T> getAll();

    boolean create(T obj);

    void update(T obj);

    void delete(int id);

    boolean isExist(int id);

}

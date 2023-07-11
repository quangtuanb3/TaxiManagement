package services;

import java.util.List;

public interface BasicCRUD<T> {
    T getById(int id);
    T getObjById(List<T> tList, String str);

    List<T> getAll();

    boolean create(T obj);

    void update(T obj);

    void delete(int id) ;

    boolean isExist(int id);

}

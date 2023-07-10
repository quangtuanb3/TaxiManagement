package services;

import java.util.List;

public interface BasicCRUD<T> {
    T getById(int id);

    List<T> getAll();

    void create(T obj);

    void update(T obj);

    void delete(int id) ;

    boolean isExist(List<T> list, int id);

}

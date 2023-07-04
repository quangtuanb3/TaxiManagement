package utils;


public interface IModel<T> {
    T parseData(String line);
    String serializeData();
}

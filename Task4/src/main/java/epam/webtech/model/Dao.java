package epam.webtech.model;

import java.util.List;

public interface Dao<T> {

    T findById(int id);
    List<T> findAll();
    void update(T object);
    void delete(T object);
}

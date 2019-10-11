package epam.webtech.repositories;

import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.NotFoundException;

import java.io.IOException;

public interface CrudRepository<T> {

    void add(T object) throws IOException, AlreadyExistsException;

    T getByID(int id) throws NotFoundException;

    void update(T object) throws NotFoundException, IOException;

    void delete(T object) throws NotFoundException, IOException;

}

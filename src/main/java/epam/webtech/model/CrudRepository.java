package epam.webtech.model;

import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.NotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public interface CrudRepository<T> {

    void add(T object) throws IOException, AlreadyExistsException;

    T getByID(int id) throws NotFoundException, AlreadyExistsException, IOException;

    void update(T object) throws NotFoundException, IOException;

    void delete(T object) throws NotFoundException, IOException;

}

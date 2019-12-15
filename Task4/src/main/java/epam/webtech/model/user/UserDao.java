package epam.webtech.model.user;

import epam.webtech.exceptions.DatabaseException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.model.Dao;

import java.util.List;

public interface UserDao extends Dao<User> {

    User findById(int id) throws DatabaseException, NotFoundException;
    User findByName(String name) throws NotFoundException, DatabaseException;
    List<User> findAll() throws DatabaseException;
    void update(User user) throws DatabaseException, NotFoundException;
    void delete(User user) throws DatabaseException, NotFoundException;
}

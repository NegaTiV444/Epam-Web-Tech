package epam.webtech.model.user;

import epam.webtech.model.Dao;

import java.util.List;

public interface UserDao extends Dao<User> {

    User findById(int id);
    User findByName(String name);
    List<User> findAll();
    void update(User user);
    void delete(User user);
}

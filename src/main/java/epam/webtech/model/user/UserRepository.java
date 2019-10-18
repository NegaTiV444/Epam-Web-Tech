package epam.webtech.model.user;

import epam.webtech.exceptions.NotFoundException;
import epam.webtech.model.CrudRepository;

public interface UserRepository extends CrudRepository<User> {

    User getByName(String name) throws NotFoundException;
}

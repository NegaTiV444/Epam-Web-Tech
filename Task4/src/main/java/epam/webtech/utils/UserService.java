package epam.webtech.utils;

import epam.webtech.exceptions.*;
import epam.webtech.model.race.MySqlRaceDao;
import epam.webtech.model.user.MySqlUserDao;
import epam.webtech.model.user.User;
import epam.webtech.model.user.UserDao;

public class UserService {

    private HashService hashService = HashService.getInstance();
    private UserDao userDao = MySqlUserDao.getInstance();

    public User approveUser(String name, String password) throws AuthorisationException, InternalException {
        try {
            User user = userDao.findByName(name);
            if (user.getPasswordHash().equals(hashService.getHashAsString(password))) {
                return user;
            } else
                throw new AuthorisationException("Wrong password");
        } catch (NotFoundException e) {
            throw new AuthorisationException("User " + name + " doesn't exist.");
        } catch (DatabaseException | HashServiceException e) {
            throw new InternalException(e.getMessage());
        }
    }

    public static UserService getInstance() {
        return UserService.SingletonHandler.INSTANCE;
    }

    private static class SingletonHandler {
        static final UserService INSTANCE = new UserService();
    }
}

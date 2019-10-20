package epam.webtech.model.user;

import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.exceptions.WrongPasswordException;
import epam.webtech.services.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private XmlUserRepository userRepository;

    @Autowired
    private HashService hashService;

    public User logIn(String name, String password) throws NotFoundException, WrongPasswordException {
        User user = userRepository.getByName(name);
        if (user.getPasswordHash().equals(hashService.getHash(password)))
            return user;
        else
            throw new WrongPasswordException("Wrong password");
    }

    public User registerNewUser(String name, String password) throws AlreadyExistsException, IOException {
        User newUser = new User();
        newUser.setName(name);
        newUser.setAuthorityLvl(1);
        newUser.setPasswordHash(hashService.getHash(password));
        newUser.setBank(10000);
        newUser.setBetsId(new ArrayList<>());
        userRepository.add(newUser);
        return newUser;
    }
}

package epam.webtech.model.user;

import epam.webtech.model.user.User;
import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.exceptions.WrongPasswordException;
import epam.webtech.model.user.XmlUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private XmlUserRepository userRepository;

    private MessageDigest md;

    public UserService() {
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //TODO log
        }
    }

    public User logIn(String name, String password) throws NotFoundException, WrongPasswordException {
            User user = userRepository.getByName(name);
            if (user.getPasswordHash().equals(getPasswordHash(password)))
                return user;
            else
                throw new WrongPasswordException();
    }

    public void registerNewUser(String name, String password) throws AlreadyExistsException, IOException {
        User newUser = new User();
        newUser.setName(name);
        newUser.setPasswordHash(getPasswordHash(password));
        userRepository.add(newUser);
    }

    private String getPasswordHash(String password){
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger bi = new BigInteger(messageDigest);
        String passwordHash = bi.toString(16);
        while (passwordHash.length() < 32) {
            passwordHash = "0" + passwordHash;
        }
        return passwordHash;
    }
}

package epam.webtech.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import epam.webtech.entities.User;
import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class XmlUserRepository implements CrudRepository<User> {

    private static final String DATA_FILE_NAME = "users.xml";

    private XmlMapper xmlMapper = new XmlMapper();
    private File dataFile;
    private Map<String, User> users;

    private XmlUserRepository() {
        dataFile = new File(DATA_FILE_NAME);
        String xml = null;
        users = new HashMap<>();
        try {
            xml = inputStreamToString(new FileInputStream(dataFile));
            List<User> userList = xmlMapper.readValue(xml, new TypeReference<List<User>>() {
            });
            userList.forEach(usr -> users.put(usr.getName(), usr));
        } catch (IOException e) {
            //TODO Add log
        }
    }

    private void updateDataFile() throws IOException {
        xmlMapper.writeValue(new File(DATA_FILE_NAME), users.values());
    }

    private String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }


    @Override
    public void add(User object) throws AlreadyExistsException, IOException {
        if (users.containsKey(object.getName()))
            throw new AlreadyExistsException("User with name " + object.getName() + " already exists");
        users.put(object.getName(), object);
        updateDataFile();
    }

    @Override
    public User getByID(int id) throws NotFoundException {
        return users.values().stream()
                .filter(x -> x.getId() == id)
                .findAny()
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
    }

    public User getByName(String name) throws NotFoundException {
        User user = users.get(name);
        if (null == user)
            throw new NotFoundException("User with name " + name + " not found");
        return user;
    }

    @Override
    public void update(User object) throws NotFoundException, IOException {
        if (users.containsKey(object.getName()))
            users.put(object.getName(), object);
        else
            throw new NotFoundException("User with name " + object.getName() + " not found");
        updateDataFile();
    }

    @Override
    public void delete(User object) throws NotFoundException, IOException {
        if (users.containsKey(object.getName()))
            users.remove(object.getName());
        else
            throw new NotFoundException("User with name " + object.getName() + " not found");
        updateDataFile();
    }
}

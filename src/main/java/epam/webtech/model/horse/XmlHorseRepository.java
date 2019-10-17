package epam.webtech.model.horse;

import epam.webtech.model.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class XmlHorseRepository implements CrudRepository<Horse> {

    private static final String DATA_FILE_NAME = "horses.xml";

    private XmlHorseRepository() {
    }

    @Override
    public void add(Horse object) {

    }

    @Override
    public Horse getByID(int id) {
        return null;
    }

    @Override
    public void update(Horse object) {

    }

    @Override
    public void delete(Horse object) {

    }
}

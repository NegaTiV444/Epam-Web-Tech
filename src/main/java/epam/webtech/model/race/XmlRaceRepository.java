package epam.webtech.model.race;

import epam.webtech.model.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class XmlRaceRepository implements CrudRepository<Race> {

    private static final String DATA_FILE_NAME = "races.xml";

    private XmlRaceRepository() {
    }


    @Override
    public void add(Race object) {

    }

    @Override
    public Race getByID(int id) {
        return null;
    }

    @Override
    public void update(Race object) {

    }

    @Override
    public void delete(Race object) {

    }
}

package epam.webtech.model.match;

import epam.webtech.model.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class XmlMatchRepository implements CrudRepository<Match> {

    private static final String DATA_FILE_NAME = "matches.xml";

    private XmlMatchRepository() {
    }

    @Override
    public void add(Match object) {

    }

    @Override
    public Match getByID(int id) {
        return null;
    }

    @Override
    public void update(Match object) {

    }

    @Override
    public void delete(Match object) {

    }
}

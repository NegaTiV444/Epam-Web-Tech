package epam.webtech.model.bet;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import epam.webtech.model.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class XmlBetRepository implements CrudRepository<Bet> {

    private static final String DATA_FILE_NAME = "bets.xml";

    private XmlMapper mapper = new XmlMapper();

    private XmlBetRepository() {
    }

    @Override
    public void add(Bet object) {

    }

    @Override
    public Bet getByID(int id) {
        return null;
    }

    @Override
    public void update(Bet object) {

    }

    @Override
    public void delete(Bet object) {

    }
}

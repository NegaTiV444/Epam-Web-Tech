package epam.webtech.model.race;

import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.ValidationException;
import epam.webtech.services.MigrationService;
import epam.webtech.services.JdbcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RaceMigrationService implements MigrationService<Race> {

    private static final String TABLE = "races";
    private static final String SELECT_QUERY = "SELECT * FROM " + TABLE + " WHERE id = ? ;";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLE
            + " (id, race_date, status, horses_names, winner) VALUES (?, ?, ?, ?, ?)";

    private final Logger logger = LogManager.getLogger(RaceMigrationService.class);
    private final JdbcService jdbcService = JdbcService.getInstance();

    private RaceMigrationService() {
    }

    private static class SingletonHandler {
        static final RaceMigrationService INSTANCE = new RaceMigrationService();
    }

    public static RaceMigrationService getInstance() {
        return RaceMigrationService.SingletonHandler.INSTANCE;
    }

    @Override
    public int migrate(List<Race> races) {
        AtomicInteger counter = new AtomicInteger();
        races.forEach(race -> {
            try {
                saveRace(race);
                counter.getAndIncrement();
            } catch (AlreadyExistsException e) {
                logger.debug(e);
                System.out.println(e.getMessage());
            }
        });
        logger.debug("Total races: " + races.size() + ", successful migrated: " + counter);
        System.out.println(counter + " races migrated");
        return counter.get();
    }

    private void saveRace(Race race) throws AlreadyExistsException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = jdbcService.getConnection().prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, race.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                throw new AlreadyExistsException("Record Race with id " + race.getId() + " already exists id database");
            }
            StringBuilder horsesString = new StringBuilder();
            for (String name : race.getHorsesNames())
                horsesString.append(name + ",");
            if (horsesString.length() > 1)
                horsesString.deleteCharAt(horsesString.length() - 1);
            preparedStatement = jdbcService.getConnection().prepareStatement(INSERT_QUERY);
            preparedStatement.setInt(1, race.getId());
            preparedStatement.setString(2, race.getDate().toString());
            preparedStatement.setInt(3, race.getStatus().getPriority());
            preparedStatement.setString(4, horsesString.toString());
            preparedStatement.setString(5, race.getWinnerHorseName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.fatal(e);
            System.exit(1);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e);
            }

        }
    }
}
